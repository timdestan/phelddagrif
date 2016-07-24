package phelddagrif
package importer

object CardTypeParser {
  import CardType._

  val cardTypes = Map[String, CardType](
      "Artifact" -> Artifact,
      "Creature" -> Creature,
      "Enchantment" -> Enchantment,
      "Instant" -> Instant,
      "Land" -> Land,
      "Planeswalker" -> Planeswalker,
      "Sorcery" -> Sorcery,
      "Tribal" -> Tribal
  )

  def tryParse(text: String): Option[CardType] =
    cardTypes.get(text.trim)
}
