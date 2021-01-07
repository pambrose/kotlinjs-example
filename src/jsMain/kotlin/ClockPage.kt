import kotlinx.browser.document
import org.w3c.dom.WebSocket

fun clockPage() {
  val host = document.location?.origin ?: "Missing document.location"
  host.replaceFirst("http:", "we:")
  WebSocket("${host.replaceFirst("http:", "ws:")}/clock").apply {
    onopen = { send("start") }
    onmessage = {
      document.getElementById("time")?.innerHTML = ClockMessage.fromJson(it.data as String).msg
      Unit  // Unit is here because lambda needs to return a dynamic
    }
  }
}