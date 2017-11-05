package phelddagrif

case class Card(
    name: String,
    cardTypes: Vector[CardType],
    subtypes: Vector[Subtype],
    manaCost: ManaCost,
    abilities: Vector[KeywordAbility],
    power: Option[PowerToughness],
    toughness: Option[PowerToughness]) {
  def isLand = cardTypes.contains(CardType.Land)

  override def toString: String = {
    val typeInfo = if (isLand) Nil else List(manaCost.toString)
    val powerToughnessInfo = for {
      p <- power
      t <- toughness
    } yield s"${p}/${t}"
    val allInfo = typeInfo ++ powerToughnessInfo.toList
    if (allInfo.isEmpty)
      name
    else
      s"${name} [${allInfo.mkString(", ")}]"
  }
}
