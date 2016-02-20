package phelddagrif

class CreatureCard(
  name:          String,
  manaCost:      ManaCost,
  subtypes:      Seq[Subtype],
  abilities:     Seq[KeywordAbility],
  val power:     Int,
  val toughness: Int
)
    extends Card(name, Seq(CardType.Creature), subtypes, manaCost, abilities)
