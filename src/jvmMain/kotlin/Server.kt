import AddItemsPage.addItemsPage
import ButtonsPage.buttonsPage
import ClockPage.clockPage
import ClockWs.clockWsEndpoint
import EndPoint.ADDITEMS
import EndPoint.BUTTONS
import EndPoint.CLOCK
import EndPoint.KEYSTROKE
import HomePage.homePage
import KeystrokeSpyPage.chatPage
import KeystrokeSpyWs.chatWsEndpoint
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.websocket.*
import kotlinx.css.CSSBuilder
import kotlinx.css.Color
import kotlinx.css.body
import kotlinx.css.color
import kotlinx.css.marginTop
import kotlinx.css.px
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

        get(KEYSTROKE.asPath()) {
          call.respondHtml { chatPage() }
        }

        clockWsEndpoint()
        chatWsEndpoint()

        get("/styles.css") {
          call.respondCss {
            body {
              //backgroundColor = Color.red
            }
            rule("li") {
              marginTop = 10.px
              //backgroundColor = Color.green
            }
            rule(".backButton") {
              marginTop = 20.px
            }
            rule("p.myclass") {
              color = Color.blue
            }
          }
        }

        static("/static") {
          files("build/distributions")
        }
      }
    }.start(wait = true)
  }

//  fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
//    style(type = ContentType.Text.CSS.toString()) {
//      +CSSBuilder().apply(builder).toString()
//    }
//  }
//
//  fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
//    style = CSSBuilder().apply(builder).toString().trim()
//  }

  suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
  }
}