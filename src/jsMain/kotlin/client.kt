import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.Node

fun main() {
  window.onload = {

    consoleTalk("Hello to the console")

    document.getElementById("root")
      ?.apply {
        val button = document.createElement("button")
          .apply {
            innerHTML = "Print to console"
            addEventListener("click", { println("Print to console was clicked") });
          }
        appendChild(button)
      }

    document.body?.append {
      div {
        button {
          onClickFunction = {
            document.body?.append {
              div {
                id = "something"
                +"Something added to the bottom"
              }
            }
          }
          +"Add something to the bottom"
        }

        button {
          onClickFunction = {
            while (true) {
              document.getElementById("something")?.remove() ?: break
            }
          }
          +"Clear items added to the bottom"
        }
      }
    }
  }
}

@ExperimentalJsExport
@JsExport
fun foo() = "HeyThere"

@ExperimentalJsExport
@JsExport
fun alertMe(msg: String) = window.alert(msg)

fun consoleTalk(msg: String) {
  println(msg)
}

fun Node.sayHello() {
  append {
    div {
      +"Hello from JS"
    }
  }
}