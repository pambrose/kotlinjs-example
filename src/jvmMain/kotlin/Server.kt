import ClockWs.clockWsEndpoint
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.websocket.*
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.onClick
import kotlinx.html.script
import kotlinx.html.title
import mu.KLogging

object Server : KLogging() {

  @JvmStatic
  fun main(args: Array<String>) {
    embeddedServer(CIO, port = 8080, host = "127.0.0.1") {
      install(CallLogging)
      install(WebSockets)

      routing {
        get("/") {
          call.respondHtml(HttpStatusCode.OK) { homePage() }
        }

        get("/addItems") {
          call.respondHtml(HttpStatusCode.OK) { addItemsPage() }
        }

        get("/clock") {
          call.respondHtml(HttpStatusCode.OK) { clockPage() }
        }

        clockWsEndpoint()

        static("/static") {
          files("build/distributions")
        }
      }
    }.start(wait = true)
  }

  fun HTML.homePage() {
    head {
      title("Hello from Ktor!")
    }
    body {
      div {
        id = "greeting"
        +"Hello from Ktor"
      }

      div {
        a { href = "/addItems"; +"Add Items" }
      }

      div {
        a { href = "/clock"; +"Clock Page" }
      }

      button {
        onClick = """kotlinjs.alertMe("alertMe() was pressed")"""
        +"Alert me"
      }

      div {
        id = "root"
      }

      script(src = "/static/jscode.js") {}
    }
  }

  fun HTML.addItemsPage() {
    head {
      title("Add Items Page")
    }
    body {
      div {
        id = "root"
      }

      button {
        onClick = """kotlinjs.goToUrl("/")"""
        +"Go Back"
      }

      script(src = "/static/jscode.js") {}
    }
  }

  fun HTML.clockPage() {
    head {
      title("Clock Page")
    }
    body {
      div {
        id = "time"
      }

      div {
        id = "inputfield"
      }

      button {
        onClick = """kotlinjs.goToUrl("/")"""
        +"Go Back"
      }

      script(src = "/static/jscode.js") {}
    }
  }
}