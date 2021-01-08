import kotlinx.html.BODY
import kotlinx.html.button
import kotlinx.html.onClick

object Utils {

  fun BODY.backButton() {
    button(classes = "backButton") {
      onClick = """kotlinjs.goToUrl("/")"""
      +"Go Back"
    }

  }
}