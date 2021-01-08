import kotlinx.browser.document
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.span

fun addItemsPage() {
  document.getElementById("root")?.append {
    div {
      span {
        button {
          +"Add something to the bottom"
          onClickFunction = {
            document.getElementById("root")?.append {
              div {
                id = "something"
                +"Something added to the bottom"
              }
            }
          }
        }

        button {
          +"Clear items added to the bottom"
          onClickFunction = {
            while (true) {
              document.getElementById("something")?.remove() ?: break
            }
          }
        }
      }
    }
  }
}