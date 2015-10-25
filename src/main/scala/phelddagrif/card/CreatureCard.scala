package phelddagrif
package card

import phelddagrif.mana.Mana

class CreatureCard(name: String,
                   manaCost: Mana,
                   val power: Int,
                   val toughness: Int)
  extends Card(name, Seq(CardType.Creature), manaCost)
