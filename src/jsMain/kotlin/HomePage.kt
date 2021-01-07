import kotlinx.browser.document

fun indexPage() {
  consoleTalk("Hello to the console")

  val button = document.createElement("button")
    .apply {
      innerHTML = "Print to console"
      addEventListener("click", { println("Print to console was clicked") })
    }
  document.getElementById("root")?.appendChild(button)
}
