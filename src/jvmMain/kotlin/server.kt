import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.onClick
import kotlinx.html.script
import kotlinx.html.title

fun HTML.myIndex() {
  head {
    title("Hello from Ktor!")
  }
  body {
    div {
      id = "greeting"
      +"Hello from Ktor"
    }
    button {
      onClick = """kotlinjs.alertMe("alertMe() was pressed")"""
      +"Alert me"

    }
    div {
      id = "root"
    }

    script(src = "/static/myjscode.js") {}
  }
}

fun main() {
  embeddedServer(CIO, port = 8080, host = "127.0.0.1") {
    install(CallLogging)
    routing {
      get("/") {
        call.respondHtml(HttpStatusCode.OK) {
          myIndex()
        }
      }
      static("/static") {
        files("build/distributions")
      }
    }
  }.start(wait = true)
}