package phelddagrif

class Card(
    val name:      String,
    val cardType:  Seq[CardType],
    val subtypes:  Seq[Subtype],
    val manaCost:  ManaCost,
    val abilities: Seq[KeywordAbility]
) {
}
