import kotlinx.browser.document

fun buttonsPage() {
  val button = document.createElement("button")
    .apply {
      innerHTML = "Print to console"
      addEventListener("click", { println("Print to console was clicked") })
    }
  document.getElementById("root")?.appendChild(button)
}