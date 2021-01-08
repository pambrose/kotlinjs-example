import EndPoint.ADDITEMS
import EndPoint.BUTTONS
import EndPoint.CLOCK
import EndPoint.KEYSTROKE
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import org.w3c.dom.Node
import org.w3c.dom.url.URL

fun main() {
  window.onload = {
    val path = URL(document.URL).pathname
    when (path) {
      "/" -> indexPage()
      BUTTONS.asPath() -> buttonsPage()
      ADDITEMS.asPath() -> addItemsPage()
      CLOCK.asPath() -> clockPage()
      KEYSTROKE.asPath() -> keystrokeSpyPage()
      else -> {
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
    div { +"Hello from JS" }
  }
}

@ExperimentalJsExport
@JsExport
fun goToUrl(url: String) {
  window.location.href = url
}