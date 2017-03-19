package phelddagrif

import fastparse.all._
import phelddagrif.parsing.NaturalNumber

// A Power or Toughness
abstract sealed trait PowerToughness

object PowerToughness {
  case class Fixed(number: Int) extends PowerToughness
  case class Var(name: String) extends PowerToughness
  case object Star extends PowerToughness
  case class Add(lhs: PowerToughness, rhs: PowerToughness)
      extends PowerToughness

  def apply(number: Int): PowerToughness = Fixed(number)

  val parser = NaturalNumber.parser.map(Fixed(_))
}
