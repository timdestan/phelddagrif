package phelddagrif

// We have a conundrum here, in that we have multiple reasonable ways to
// represent the zero mana cost that will not compare equal with one another:
// The {0} symbol, and an empty vector. TODO: Resolve this discrepancy.
case class ManaCost(symbols: Vector[ManaCost.ManaSymbol]) {
  def colors: Set[Color] = symbols.flatMap { _.colors }.toSet
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

  def of(symbols: ManaSymbol*) = ManaCost(symbols.toVector)

  // The zero mana cost.
  val Zero: ManaCost = ManaCost.of(FixedGeneric(0))
}
