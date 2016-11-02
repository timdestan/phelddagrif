package phelddagrif

import cats.data.{NonEmptyList, Xor}
import fastparse.all._

sealed trait ManaCost {
  def colors: Set[Color]
}

object ManaCost {
  // A single component of a mana cost
  sealed trait ManaSymbol {
    def colors: Set[Color] = this match {
      case FixedGeneric(_) => Set.empty
      case VariableGeneric => Set.empty
      case Colored(c) => Set(c)
      case Hybrid(l, r) => l.colors union r.colors
      case Phyrexian(c) => Set(c)
    }
  }

  object ManaSymbol {
    val fixedGenericParser =
      P(CharIn('0' to '9').!).map(num => FixedGeneric(num.toInt))
    val coloredParser = Color.parser.map(Colored(_))
    val variableGenericParser = P("X").map(_ => VariableGeneric)

    val parser:Parser[ManaSymbol] =
      P(coloredParser |
        fixedGenericParser |
        variableGenericParser).opaque("Mana symbol")

    // TODO: Handle the other types of symbols.
  }

  case class FixedGeneric(amount: Int) extends ManaSymbol
  case object VariableGeneric extends ManaSymbol
  case class Colored(color: Color) extends ManaSymbol
  case class Hybrid(left: ManaSymbol, right: ManaSymbol) extends ManaSymbol
  case class Phyrexian(color: Color) extends ManaSymbol

  // TODO: Support for snow mana costs

  // Shorthand for the colored mana symbols.
  val White = Colored(Color.White)
  val Blue = Colored(Color.Blue)
  val Green = Colored(Color.Green)
  val Red = Colored(Color.Red)
  val Black = Colored(Color.Black)

  // As with the symbols printed on cards, here the zero mana cost is
  // represented as a {0} mana symbol, not an empty list of symbols.
  private case class ManaCostImpl(symbols: NonEmptyList[ManaCost.ManaSymbol])
      extends ManaCost {
    def colors: Set[Color] = symbols.map { _.colors }.reduceLeft(_ ++ _)
  }

  def apply(symbols: ManaSymbol*): ManaCost = apply(symbols.toList)
  def apply(symbols: List[ManaSymbol]): ManaCost =
    ManaCostImpl(NonEmptyList
      .fromList(symbols)
      .getOrElse(NonEmptyList.of(FixedGeneric(0))))

  // The zero mana cost.
  val Zero: ManaCost = ManaCost()

  val parser:Parser[ManaCost] =
    P( "{" ~ ManaSymbol.parser ~ "}")
      .rep.map(seq => ManaCost(seq.toList))
}
