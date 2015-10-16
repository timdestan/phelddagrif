package phelddagrif
package mana

import phelddagrif.colors.Color
import phelddagrif.colors.Color.Color

// 106.1b There are six types of mana: white, blue, black, red, green, and
//        colorless.

sealed trait ManaType {
  // Create shortcuts for the five colored mana types.
  val White = Colored(Color.White)
  val Blue = Colored(Color.Blue)
  val Black = Colored(Color.Black)
  val Red = Colored(Color.Red)
  val Green = Colored(Color.Green)
}

case object Colorless extends ManaType
case class Colored(color: Color) extends ManaType
