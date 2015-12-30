package phelddagrif

// 202.1a The mana cost of an object represents what a player must spend from
// his or her mana pool to cast that card. Unless an object’s mana cost
// includes Phyrexian mana symbols (see rule 107.4f), paying that mana cost
// requires matching the color of any colored mana symbols as well as paying
// the generic mana indicated in the cost.

class ManaCost(components: Seq[ManaCost.Component]) {
  def colors: Set[Color] = components.flatMap { _.colors }.toSet
}

object ManaCost {
  // A single component of a mana cost
  sealed trait Component {
    def colors: Set[Color] = this match {
      case FixedGeneric(_) => Set.empty
      case VariableGeneric => Set.empty
      case Colored(_, c) => Set(c)
      case Hybrid(l, r) => l.colors union r.colors
      case Phyrexian(c) => Set(c)
    }
  }

  // 107.4b Numeral symbols (such as {1}) and variable symbols (such as {X})
  // represent generic mana in costs. Generic mana in costs can be paid with
  // any type of mana. For more information about {X}, see rule 107.3.
  case class FixedGeneric(amount: Int) extends Component
  case object VariableGeneric extends Component

  // 107.4a ... Colored mana in costs can be paid only with the appropriate
  // color of mana. ...
  case class Colored(amount: Int, color: Color) extends Component

  // 107.4e Hybrid mana symbols are also colored mana symbols. Each one
  // represents a cost that can be paid in one of two ways, as represented by
  // the two halves of the symbol. A hybrid symbol such as {W/U} can be paid
  // with either white or blue mana, and a monocolored hybrid symbol such as
  // {2/B} can be paid with either one black mana or two mana of any type. A
  // hybrid mana symbol is all of its component colors.
  case class Hybrid(left: Component, right: Component) extends Component

  // 107.4f Phyrexian mana symbols are colored mana symbols: {W/P} is white,
  // {U/P} is blue, {B/P} is black, {R/P} is red, and {G/P} is green. A
  // Phyrexian mana symbol represents a cost that can be paid either with one
  // mana of its color or by paying 2 life.
  case class Phyrexian(color: Color) extends Component

  // TODO: Support for snow mana costs

  // ----------------------------------------------- //
  //                  Helpers                        //
  // ----------------------------------------------- //

  def apply(components: Component*):ManaCost = new ManaCost(components.toVector)

  // The zero mana cost.
  val Zero:ManaCost = ManaCost(FixedGeneric(0))
}
