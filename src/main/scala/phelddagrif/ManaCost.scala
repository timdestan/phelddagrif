package phelddagrif

import cats.data.NonEmptyList
import fastparse.all._
import fastparse.all.{P => mkParser}

sealed trait ManaCost {
  def colors: Set[Color]
}

object ManaCost {
  // A single component of a mana cost
  sealed trait ManaSymbol { self =>
    def colors: Set[Color] = this match {
      case FixedGeneric(_) => Set.empty
      case VariableGeneric => Set.empty
      case Colored(c) => Set(c)
      case Hybrid(l, r) => l.colors union r.colors
      case Phyrexian => Set.empty
      case Colorless => Set.empty
    }

    def /(other: ManaSymbol): ManaSymbol = Hybrid(self, other)
  }

  object ManaSymbol {
    val fixedGenericParser =
      mkParser(CharIn('0' to '9').rep(1).!).map(num => FixedGeneric(num.toInt))
    val coloredParser = Color.parser.map(Colored(_))
    val variableGenericParser = mkParser("X").map(_ => VariableGeneric)
    val phyrexianParser = mkParser("P").map(_ => Phyrexian)
    val colorlessParser = mkParser("C").map(_ => Colorless)

    val singleSymbolParser =
       mkParser(coloredParser |
                fixedGenericParser |
                variableGenericParser |
                phyrexianParser |
                colorlessParser)

    val hybridParser =
      mkParser(singleSymbolParser ~ "/" ~ singleSymbolParser)
          .map({ case (c1, c2) => c1 / c2 })

    val parser:Parser[ManaSymbol] =
      mkParser(hybridParser | singleSymbolParser)

    // TODO: Handle the other types of symbols.
  }

  final case class FixedGeneric(amount: Int) extends ManaSymbol
  final case object VariableGeneric extends ManaSymbol
  final case class Colored(color: Color) extends ManaSymbol
  final case class Hybrid(left: ManaSymbol, right: ManaSymbol)
      extends ManaSymbol
  final case object Phyrexian extends ManaSymbol
  final case object Colorless extends ManaSymbol

  // TODO: Support for snow mana costs

  // Shorthand for the colored mana symbols.
  val White = Colored(Color.White)
  val Blue = Colored(Color.Blue)
  val Green = Colored(Color.Green)
  val Red = Colored(Color.Red)
  val Black = Colored(Color.Black)

  val W = White
  val U = Blue
  val G = Green
  val R = Red
  val B = Black
  val P = Phyrexian
  val C = Colorless

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
    mkParser( "{" ~ ManaSymbol.parser ~ "}").rep
        .map(seq => ManaCost(seq.toList))
}
