import EndPoint.ADDITEMS
import EndPoint.BUTTONS
import EndPoint.CHAT
import EndPoint.CLOCK
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.style
import kotlinx.html.title
import kotlinx.html.ul

object HomePage {
  fun HTML.homePage() {
    head {
      title("Hello from Ktor!")
      link(rel = "stylesheet", href = "/styles.css", type = "text/css")
    }

    body {
      h1 { +"Hello from Ktor" }

      ul {
        style = "padding-left:10; margin-top:0; list-style-type:circle;"
        li {
          a { href = BUTTONS.asPath(); +"Buttons" }
        }
        li {
          a { href = ADDITEMS.asPath(); +"Add Items" }
        }
        li {
          a { href = CLOCK.asPath(); +"Clock Page" }
        }
        li {
          a { href = CHAT.asPath(); +"Chat Page" }
        }
      }
    }
  }
}