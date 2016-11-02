package phelddagrif

import cats.data.{NonEmptyList, Xor}
import phelddagrif.parse._

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
    val parsers = List[Parser[ManaSymbol]](
        // Parse fixed generic mana costs.
        new Parser[ManaSymbol] {
          def parse(str: String): Error Xor ManaSymbol =
            Error.catchNonFatal(FixedGeneric(str.toInt))
        },
        // Parse colored mana costs.
        Color.parser.map(Colored(_)),
        // Parse generic mana costs.
        KeywordParser(VariableGeneric, "X")
        // TODO: Handle the other types of symbols.
    )

    val parser = new UnionParser[ManaSymbol](parsers) {
      override def error(found: String): Error =
        Error(s"Expected mana symbol. Found $found")
    }

    def parse(str: String): Error Xor ManaSymbol = parser.parse(str)
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

  // Try to parse a mana cost from a string.
  def parse(str: String): Error Xor ManaCost = {
    val parsedSymbols = str
      .replace("[{}]", "")
      .filter { x =>
        x != '{' && x != '}'
      }
      .map { _.toString }
      .map(str => ManaSymbol.parse(str))

    // Fail on the first symbol that failed to parse.
    parsedSymbols
      .foldLeft[Error Xor List[ManaSymbol]](Xor.Right(Nil)) {
        case (e @ Xor.Left(_), _) => e
        case (_, e @ Xor.Left(_)) => e
        case (Xor.Right(symbolList), Xor.Right(symbol)) =>
          Xor.Right(symbol :: symbolList)
      }
      .map(symbols => ManaCost(symbols.reverse))
  }
}
