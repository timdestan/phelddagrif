package phelddagrif

// 105.1. There are five colors in the Magic game: white, blue, black, red, and
//        green.

abstract trait Color

object Color {
  case object White extends Color
  case object Blue extends Color
  case object Black extends Color
  case object Red extends Color
  case object Green extends Color
}
