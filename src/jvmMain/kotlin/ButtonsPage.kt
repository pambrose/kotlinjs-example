import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.onClick
import kotlinx.html.script
import kotlinx.html.title

object ButtonsPage {
  fun HTML.buttonsPage() {
    head {
      title("Button Page")
    }

    body {
      button {
        onClick = """kotlinjs.alertMe("alertMe() was pressed")"""
        +"Alert me"
      }

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
}