import AddItemsPage.addItemsPage
import ButtonsPage.buttonsPage
import ChatPage.chatPage
import ChatWs.chatWsEndpoint
import ClockPage.clockPage
import ClockWs.clockWsEndpoint
import EndPoint.ADDITEMS
import EndPoint.BUTTONS
import EndPoint.CHAT
import EndPoint.CLOCK
import HomePage.homePage
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.websocket.*
import mu.KLogging

object Server : KLogging() {

  @JvmStatic
  fun main(args: Array<String>) {
    embeddedServer(CIO, port = 8080, host = "127.0.0.1") {
      install(CallLogging)
      install(WebSockets)

      routing {
        get("/") {
          call.respondHtml { homePage() }
        }

        get(BUTTONS.asPath()) {
          call.respondHtml { buttonsPage() }
        }

        get(ADDITEMS.asPath()) {
          call.respondHtml { addItemsPage() }
        }

        get(CLOCK.asPath()) {
          call.respondHtml { clockPage() }
        }

        get(CHAT.asPath()) {
          call.respondHtml { chatPage() }
        }

        clockWsEndpoint()
        chatWsEndpoint()

        static("/static") {
          files("build/distributions")
        }
      }
    }.start(wait = true)
  }
}