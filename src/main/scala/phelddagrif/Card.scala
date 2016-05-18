package phelddagrif

case class Card(
  name:      String,
  cardType:  Vector[CardType],
  subtypes:  Vector[Subtype],
  manaCost:  ManaCost,
  abilities: Vector[KeywordAbility]
)
