package phelddagrif

import cats.data.NonEmptyList
import fastparse.all._
import fastparse.all.{P => mkParser}

sealed trait ManaType

case object Colorless            extends ManaType
case class Colored(color: Color) extends ManaType

object ManaType {
  // Create shortcuts for the five colored mana types.
  val White = Colored(Color.White)
  val Blue  = Colored(Color.Blue)
  val Black = Colored(Color.Black)
  val Red   = Colored(Color.Red)
  val Green = Colored(Color.Green)
}

sealed trait ManaCost {
  def colors: Set[Color]
}

object ManaCost {
  // A single component of a mana cost
  sealed trait ManaSymbol { self =>
    def colors: Set[Color] = this match {
      case FixedGeneric(_)    => Set.empty
      case VariableGeneric(_) => Set.empty
      case Colored(c)         => Set(c)
      case Hybrid(l, r)       => l.colors.union(r.colors)
      case Phyrexian          => Set.empty
      case Colorless          => Set.empty
    }

    def /(other: ManaSymbol): ManaSymbol = Hybrid(self, other)
    def /(genericCost: Int): ManaSymbol =
      Hybrid(self, FixedGeneric(genericCost))

    override def toString = this match {
      case FixedGeneric(n)      => n.toString
      case VariableGeneric(sym) => sym
      case Colored(c)           => c.toString
      case Hybrid(l, r)         => s"${l}/${r}"
      case Phyrexian            => "P"
      case Colorless            => "C"
    }
  }

  object ManaSymbol {
    val fixedGenericParser = NumberParsers.natural.map(FixedGeneric(_))
    val coloredParser      = Color.parser.map(Colored(_))
    val variableGenericParser =
      mkParser("X" | "Y" | "Z").!.map(name => VariableGeneric(name))
    val phyrexianParser = mkParser("P").map(_ => Phyrexian)
    val colorlessParser = mkParser("C").map(_ => Colorless)

    val singleSymbolParser =
      mkParser(
        coloredParser |
          fixedGenericParser |
          variableGenericParser |
          phyrexianParser |
          colorlessParser)

    val hybridParser =
      mkParser(singleSymbolParser ~ "/" ~ singleSymbolParser)
        .map({ case (c1, c2) => c1 / c2 })

    val parser: Parser[ManaSymbol] =
      mkParser(hybridParser | singleSymbolParser)

    // TODO: Handle the other types of symbols.
  }

  final case class FixedGeneric(amount: Int) extends ManaSymbol
  final case class VariableGeneric(name: String /* usually "X" */ )
      extends ManaSymbol
  final case class Colored(color: Color) extends ManaSymbol
  final case class Hybrid(left: ManaSymbol, right: ManaSymbol)
      extends ManaSymbol
  // Note that the colored phyrexian mana symbols (e.g. {R/P}) are implemented
  // as a hybrid cost of either {P} or the appropriate color symbol.
  final case object Phyrexian extends ManaSymbol
  final case object Colorless extends ManaSymbol

  val White = Colored(Color.White)
  val Blue  = Colored(Color.Blue)
  val Black = Colored(Color.Black)
  val Red   = Colored(Color.Red)
  val Green = Colored(Color.Green)

  val W = White
  val U = Blue
  val B = Black
  val R = Red
  val G = Green

  val P = Phyrexian
  val C = Colorless

  // As with the symbols printed on cards, here the zero mana cost is
  // represented as a {0} mana symbol, not an empty list of symbols.
  private case class ManaCostImpl(symbols: NonEmptyList[ManaCost.ManaSymbol])
      extends ManaCost {
    def colors: Set[Color] = symbols.map { _.colors }.reduceLeft(_ ++ _)

    override def toString =
      symbols
        .map(sym => "{" + sym.toString + "}")
        .toList
        .mkString("")
  }

  def apply(symbols: ManaSymbol*): ManaCost = apply(symbols.toList)
  def apply(symbols: List[ManaSymbol]): ManaCost =
    ManaCostImpl(
      NonEmptyList
        .fromList(symbols)
        .getOrElse(NonEmptyList.of(FixedGeneric(0))))

  // For, e.g., ManaCost(4, G, G)
  def apply(genericCost: Int, symbols: ManaSymbol*): ManaCost =
    ManaCostImpl(NonEmptyList(FixedGeneric(genericCost), symbols.toList))

  // The zero mana cost.
  val Zero: ManaCost = ManaCost()

  val parser: Parser[ManaCost] =
    mkParser("{" ~ ManaSymbol.parser ~ "}").rep
      .map(seq => ManaCost(seq.toList))
}

case class Mana(amount: Int, manaType: ManaType)

case class ManaPool(mana: Set[Mana]) {
  def add(m: Mana): ManaPool = ManaPool(mana + m)
  def add(number: Int, manaType: ManaType): ManaPool =
    ManaPool(mana + Mana(number, manaType))

  def simplify: ManaPool = ManaPool(
    mana
      .groupBy { _.manaType }
      .map {
        case (typ, counts) => Mana(counts.map(_.amount).sum, typ)
      }
      .toSet
  )
}

object ManaPool {
  val empty: ManaPool = new ManaPool(Set())

  def of(manas: Tuple2[Int, ManaType]*): ManaPool =
    manas.foldLeft(ManaPool.empty) {
      case (pool, (num, manaType)) => pool.add(num, manaType)
    }
}
