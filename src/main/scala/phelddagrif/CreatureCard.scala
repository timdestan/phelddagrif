package phelddagrif

class CreatureCard(name: String,
                   manaCost: Seq[Mana],
                   val power: Int,
                   val toughness: Int)
  extends Card(name, Seq(CardType.Creature), manaCost)
