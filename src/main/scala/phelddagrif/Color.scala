package phelddagrif

sealed class Color(val symbol: String)

object Color {
  case object White extends Color("W")
  case object Blue extends Color("U")
  case object Black extends Color("B")
  case object Red extends Color("R")
  case object Green extends Color("G")

  val allColors = List(White, Blue, Black, Red, Green)

  def parse(text: String): Option[Color] =
    allColors.find { _.symbol == text }
}
