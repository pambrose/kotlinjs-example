import Utils.backButton
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.script
import kotlinx.html.title

object AddItemsPage {
  fun HTML.addItemsPage() {
    head {
      title("Add Items Page")
      link(rel = "stylesheet", href = "/styles.css", type = "text/css")
    }

    body {
      div {
        id = "root"
      }

      backButton()

      script(src = "/static/jscode.js") {}
    }
  }
}