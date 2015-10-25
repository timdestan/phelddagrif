package phelddagrif
package card

import phelddagrif.mana.Mana
import phelddagrif.card.CardType.CardType

class Card(val name: String,
           val cardType: Seq[CardType],
           val manaCost: Mana) {
}
