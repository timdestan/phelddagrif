package phelddagrif

class ManaCost(symbols: Seq[ManaCost.ManaSymbol]) {
  def colors: Set[Color] = symbols.flatMap { _.colors }.toSet
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

  case class FixedGeneric(amount: Int) extends ManaSymbol
  case object VariableGeneric extends ManaSymbol
  case class Colored(color: Color) extends ManaSymbol
  case class Hybrid(left: ManaSymbol, right: ManaSymbol) extends ManaSymbol
  case class Phyrexian(color: Color) extends ManaSymbol

  // TODO: Support for snow mana costs

  def apply(symbols: ManaSymbol*):ManaCost = new ManaCost(symbols)

  // The zero mana cost.
  val Zero:ManaCost = ManaCost(FixedGeneric(0))
}
