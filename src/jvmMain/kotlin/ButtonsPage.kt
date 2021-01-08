import Utils.backButton
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.onClick
import kotlinx.html.script
import kotlinx.html.title

object ButtonsPage {
  fun HTML.buttonsPage() {
    head {
      title("Button Page")
      link(rel = "stylesheet", href = "/styles.css", type = "text/css")
    }

    body {
      button {
        onClick = """kotlinjs.alertMe("alertMe() was pressed")"""
        +"Alert me"
      }

      div {
        id = "root"
      }

      backButton()

      script(src = "/static/jscode.js") {}
    }
  }
}