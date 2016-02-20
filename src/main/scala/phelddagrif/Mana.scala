package phelddagrif

case class Mana(amount: Int, manaType: ManaType)

object Mana {
  implicit class RichNumber(number: Int) {
    // Produces an amount of mana equal to ${number} amount of the given mana
    // type.
    def *(manaType: ManaType) = Mana(number, manaType)
  }
}
