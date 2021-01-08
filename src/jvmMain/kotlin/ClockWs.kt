import EndPoint.CLOCK
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
import java.util.concurrent.TimeUnit
import kotlin.collections.LinkedHashSet
import kotlin.concurrent.timer
import kotlin.time.Duration
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
          val elapsed = sessionContext.start.elapsedNow().format()
          val formatted = "${current.format(formatter)} elapsed: $elapsed"
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
    webSocket(CLOCK.asPath()) {
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

  fun Duration.format(includeMillis: Boolean = false): String {
    val diff = toLongMilliseconds()
    val day = TimeUnit.MILLISECONDS.toDays(diff)
    val dayMillis = TimeUnit.DAYS.toMillis(day)
    val hr = TimeUnit.MILLISECONDS.toHours(diff - dayMillis)
    val hrMillis = TimeUnit.HOURS.toMillis(hr)
    val min = TimeUnit.MILLISECONDS.toMinutes(diff - dayMillis - hrMillis)
    val minMillis = TimeUnit.MINUTES.toMillis(min)
    val sec = TimeUnit.MILLISECONDS.toSeconds(diff - dayMillis - hrMillis - minMillis)
    val secMillis = TimeUnit.SECONDS.toMillis(sec)
    val ms = TimeUnit.MILLISECONDS.toMillis(diff - dayMillis - hrMillis - minMillis - secMillis)

    return if (includeMillis)
      String.format("%d:%02d:%02d:%02d.%03d", day, hr, min, sec, ms)
    else
      String.format("%d:%02d:%02d:%02d", day, hr, min, sec)
  }
}