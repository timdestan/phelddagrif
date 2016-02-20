package phelddagrif

sealed trait Color

object Color {
  case object White extends Color
  case object Blue extends Color
  case object Black extends Color
  case object Red extends Color
  case object Green extends Color
}
