enum class EndPoint {

  BUTTONS,
  ADDITEMS,
  KEYSTROKE,
  CLOCK;

  fun asPath() = "/${name.toLowerCase()}"
}