package phelddagrif

import fastparse.all._

sealed class Color(val symbol: String)

object Color {
  case object White extends Color("W")
  case object Blue extends Color("U")
  case object Black extends Color("B")
  case object Red extends Color("R")
  case object Green extends Color("G")

  val parser:P[Color] =
      List(White, Blue, Black, Red, Green)
        .map(color => P(color.symbol).map(_ => color))
        .reduce(_ | _)
}
