package phelddagrif

sealed trait ManaCost {
  def colors: Set[Color]
}

object ManaCost {
  // A single component of a mana cost
  sealed trait ManaSymbol {
    def colors: Set[Color] = this match {
      case FixedGeneric(_) ⇒ Set.empty
      case VariableGeneric ⇒ Set.empty
      case Colored(c)      ⇒ Set(c)
      case Hybrid(l, r)    ⇒ l.colors union r.colors
      case Phyrexian(c)    ⇒ Set(c)
    }
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

  // Invariant (enforced by apply): symbols is always non-empty. The zero mana
  // cost is represented with a single symbol: FixedGeneric(0).
  private case class ManaCostImpl(symbols: List[ManaCost.ManaSymbol]) extends ManaCost {
    def colors: Set[Color] = symbols.flatMap { _.colors }.toSet
  }

  def apply(symbols: ManaSymbol*): ManaCost = apply(symbols.toList)
  def apply(symbols: List[ManaSymbol]): ManaCost =
    ManaCostImpl(if (symbols.isEmpty) List(FixedGeneric(0)) else symbols)

  // The zero mana cost.
  val Zero: ManaCost = ManaCost()
}
