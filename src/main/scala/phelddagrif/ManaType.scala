package phelddagrif

sealed trait ManaType

case object Colorless extends ManaType
case class Colored(color: Color) extends ManaType

object ManaType {
  // Create shortcuts for the five colored mana types.
  val White = Colored(Color.White)
  val Blue = Colored(Color.Blue)
  val Black = Colored(Color.Black)
  val Red = Colored(Color.Red)
  val Green = Colored(Color.Green)
}
