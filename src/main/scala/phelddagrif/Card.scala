package phelddagrif

import CardType.CardType

class Card(val name: String,
           val cardType: Seq[CardType],
           val manaCost: Mana) {
}
