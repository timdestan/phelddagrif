package phelddagrif

class CreatureCard(
  name:          String,
  manaCost:      ManaCost,
  subtypes:      Vector[Subtype],
  abilities:     Vector[KeywordAbility],
  val power:     Int,
  val toughness: Int
) extends Card(
  name,
  Vector(CardType.Creature),
  subtypes,
  manaCost,
  abilities
)
