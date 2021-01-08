import EndPoint.CHAT
import kotlinx.browser.document
import kotlinx.html.dom.append
import kotlinx.html.js.input
import kotlinx.html.js.onKeyPressFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.WebSocket
import org.w3c.dom.events.KeyboardEvent

fun chatPage() {
  val host = document.location?.origin ?: "Missing document.location"
  host.replaceFirst("http:", "ws:")
  val ws =
    WebSocket("${host.replaceFirst("http:", "ws:")}${CHAT.asPath()}").apply {
      onopen = { send("start") }
      onmessage = {
        document.getElementById("time")?.innerHTML = ClockMessage.fromJson(it.data as String).msg
        Unit  // Unit is here because lambda needs to return a dynamic
      }
    }

  document.getElementById("chatfield")?.append {
    input {
      onKeyPressFunction = { event ->
        val target = event.target
        if (target is HTMLInputElement && event is KeyboardEvent) {
          println(event.key)
          ws.send(event.key)
        }
      }
    }
  }
}