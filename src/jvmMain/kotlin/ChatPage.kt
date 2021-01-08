import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.onClick
import kotlinx.html.script
import kotlinx.html.title

object ChatPage {
  fun HTML.chatPage() {
    head {
      title("Chat Page")
    }

    body {
      div {
        id = "chatfield"
      }

      button {
        onClick = """kotlinjs.goToUrl("/")"""
        +"Go Back"
      }

      script(src = "/static/jscode.js") {}
    }
  }
}