import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking
import mu.KLogging
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.LinkedHashSet
import kotlin.concurrent.timer
import kotlin.time.TimeSource
import kotlin.time.seconds

object ClockWs : KLogging() {
  private val clock = TimeSource.Monotonic
  private val wsConnections = Collections.synchronizedSet(LinkedHashSet<SessionContext>())
  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  data class SessionContext(val wsSession: DefaultWebSocketServerSession) {
    val start = clock.markNow()
  }

  init {
    timer("clock msg sender", false, 0L, 1.seconds.toLongMilliseconds()) {
      for (sessionContext in wsConnections)
        try {
          val current = LocalDateTime.now()
          val formatted = current.format(formatter)
          val json = ClockMessage(formatted).toJson()
          runBlocking {
            sessionContext.wsSession.outgoing.send(Frame.Text(json))
          }
        } catch (e: Throwable) {
          logger.error { "Exception in pinger ${e.javaClass.simpleName} ${e.message}" }
        }
    }
  }

  fun Routing.clockWsEndpoint() {
    webSocket("/clock") {
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
        logger.info { "Closed clock websocket: ${wsConnections.size}" }
      }
    }
  }

  fun DefaultWebSocketServerSession.closeChannels() {
    outgoing.close()
    incoming.cancel()
  }
}