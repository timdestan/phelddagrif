package phelddagrif

import phelddagrif.parse._

sealed class Color(val symbol: String)

object Color {
  case object White extends Color("W")
  case object Blue extends Color("U")
  case object Black extends Color("B")
  case object Red extends Color("R")
  case object Green extends Color("G")

  val parser = new UnionParser[Color](
    List(White, Blue, Black, Red, Green)
        .map(color => new KeywordParser(color, color.symbol))
  )

  def parse(text: String): Option[Color] = parser.parseOption(text)
}
