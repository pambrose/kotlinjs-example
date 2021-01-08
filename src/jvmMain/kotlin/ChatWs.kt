import EndPoint.CHAT
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.mapNotNull
import mu.KLogging
import java.util.*
import kotlin.collections.LinkedHashSet

object ChatWs : KLogging() {
  private val wsConnections = Collections.synchronizedSet(LinkedHashSet<SessionContext>())

  data class SessionContext(val wsSession: DefaultWebSocketServerSession)

  fun Routing.chatWsEndpoint() {
    webSocket(CHAT.asPath()) {
      val wsContext = SessionContext(this)
      try {
        outgoing.invokeOnClose {
          incoming.cancel()
        }
        wsConnections += wsContext
        incoming
          .consumeAsFlow()
          .mapNotNull { it as? Frame.Text }
          .collect {
            logger.info { String(it.data) }
          }
      } finally {
        wsConnections -= wsContext
        closeChannels()
        close(CloseReason(CloseReason.Codes.GOING_AWAY, "Client disconnected"))
        logger.info { "Closed chat websocket: ${wsConnections.size}" }
      }
    }
  }

  fun DefaultWebSocketServerSession.closeChannels() {
    outgoing.close()
    incoming.cancel()
  }

}