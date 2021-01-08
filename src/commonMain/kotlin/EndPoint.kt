enum class EndPoint {

  BUTTONS,
  ADDITEMS,
  CHAT,
  CLOCK;

  fun asPath() = "/${name.toLowerCase()}"

}