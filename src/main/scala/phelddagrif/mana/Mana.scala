package phelddagrif
package mana

// 106.1. Mana is the primary resource in the game. Players spend mana to pay
//        costs, usually when casting spells and activating abilities.

case class Mana(amount: Int, manaType: ManaType)

object Mana {
  implicit class RichNumber(number: Int) {
    // Produces an amount of mana equal to ${number} amount of the given mana
    // type.
    def *(manaType: ManaType) = Mana(number, manaType)
  }
}
