import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.button
import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlinx.html.js.onClickFunction

fun buttonsPage() {
  val button1 = document.createElement("button")
    .apply {
      innerHTML = "Print to console"
      addEventListener("click", { println("Print to console was clicked") })
    }
  document.getElementById("root")?.appendChild(button1)

  val button2 = document.create.div {
    button {
      +"Click me"
      onClickFunction = {
        window.alert("I was clicked")
      }
    }
  }
  document.getElementById("root")?.appendChild(button2)
}