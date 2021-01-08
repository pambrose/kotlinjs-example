import Utils.backButton
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.script
import kotlinx.html.title

object ChatPage {
  fun HTML.chatPage() {
    head {
      title("Chat Page")
      link(rel = "stylesheet", href = "/styles.css", type = "text/css")
    }

    body {
      div {
        id = "chatfield"
      }

      backButton()

      script(src = "/static/jscode.js") {}
    }
  }
}