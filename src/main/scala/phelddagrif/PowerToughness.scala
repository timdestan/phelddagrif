package phelddagrif

import fastparse.all._
import phelddagrif.parsing.Integer

// A Power or Toughness
abstract sealed trait PowerToughness {
  import PowerToughness._

  override def toString = this match {
    case Fixed(n) => n.toString
    case Star => "*"
    case Add(n, m) => s"${n}+${m}"
    case Subtract(n, m) => s"${n}-${m}"
  }
}

object PowerToughness {
  case class Fixed(number: Int) extends PowerToughness
  case object Star extends PowerToughness
  case class Add(lhs: PowerToughness, rhs: PowerToughness)
      extends PowerToughness
  case class Subtract(lhs: PowerToughness, rhs: PowerToughness)
      extends PowerToughness

  def apply(number: Int): PowerToughness = Fixed(number)

  // Can be negative: Some creatures have negative power.
  val fixedParser = Integer.parser.map(Fixed(_))
  var starParser = P("*").map(_ => Star)
  val simpleParser = P(fixedParser | starParser)

  val addParser = P(simpleParser ~ "+" ~ simpleParser).map {
    case (l, r) => Add(l, r)
  }
  val subtractParser = P(simpleParser ~ "-" ~ simpleParser).map {
    case (l, r) => Subtract(l, r)
  }

  val parser = P(addParser | subtractParser | simpleParser)
}
