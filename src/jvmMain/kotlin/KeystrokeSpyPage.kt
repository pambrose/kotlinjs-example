import Utils.backButton
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.textArea
import kotlinx.html.title

object KeystrokeSpyPage {
  fun HTML.chatPage() {
    head {
      title("Keystroke Spy Page")
      link(rel = "stylesheet", href = "/styles.css", type = "text/css")
    }

    body {
      p {
        +"As you type here, your keystrokes will be sent to the server"
      }

      div {
        id = "inputfield"
      }

      p {
        +"And they will be sent back to the client here:"
      }

      p {
        textArea {
          readonly = true
          id = "outputfield"
          rows = "20"
          cols = "120"
          +""
        }
      }

      backButton()

      script(src = "/static/jscode.js") {}
    }
  }
}