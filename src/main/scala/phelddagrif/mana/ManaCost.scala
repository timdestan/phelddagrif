package phelddagrif
package mana

// 202.1a The mana cost of an object represents what a player must spend from
//        his or her mana pool to cast that card. Unless an object’s mana cost
//        includes Phyrexian mana symbols (see rule 107.4f), paying that mana
//        cost requires matching the color of any colored mana symbols as well
//        as paying the generic mana indicated in the cost.
//
// 202.1b Some objects have no mana cost. This normally includes all land cards,
//        any other cards that have no mana symbols where their mana cost would
//        appear, tokens (unless the effect that creates them specifies
//        otherwise), and nontraditional Magic cards. Having no mana cost
//        represents an unpayable cost (see rule 117.6). Note that lands are
//        played without paying any costs (see rule 305, “Lands”).

sealed trait ManaCost {
  // Combines with another mana cost.
  def +(other: ManaCost):ManaCost = CombinedCost(Seq(this, other))
  // Return the converted mana cost
  def convertedManaCost:Int
}

// Represents a cost that is a given amount of mana of a single type.
case class CostOfType(amount: Int, manaType: ManaType) extends ManaCost {
  def convertedManaCost = amount
}

// Represents a cost formed by combining many mana costs.
case class CombinedCost(costs: Seq[ManaCost]) extends ManaCost {
  def convertedManaCost = costs.map(_.convertedManaCost).sum
}

object ManaCost {
  implicit class RichNumber(number: Int) {
     // Produces a mana cost equal to ${number} amount of the given mana type.
    def *(manaType: ManaType) = CostOfType(number, manaType)
  }
}
