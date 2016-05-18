package phelddagrif
package importer

// For now we only look for keyword abilities.
case class ParsedRulesText(
  keywordAbilities: Vector[KeywordAbility]
)

object ParsedRulesText {
  // Empty card text.
  lazy val empty = new ParsedRulesText(Vector())
}

object ParsedKeywordAbility {
  // Map from name to keyword abilities for keywords that have
  // no modifiers.
  def nullaryKeywordAbilities = Map[String, KeywordAbility](
    "Deathtouch" → Deathtouch,
    "Defender" → Defender,
    "Double Strike" → DoubleStrike,
    "First Strike" → FirstStrike,
    "Flash" → Flash,
    "Flying" → Flying,
    "Haste" → Haste,
    "Hexproof" → Hexproof,
    "Indestructible" → Indestructible,
    "Intimidate" → Intimidate,
    "Lifelink" → Lifelink,
    "Reach" → Reach,
    "Shroud" → Shroud,
    "Trample" → Trample,
    "Vigilance" → Vigilance,
    "Banding" → Banding,
    "Flanking" → Flanking,
    "Phasing" → Phasing,
    "Shadow" → Shadow,
    "Horsemanship" → Horsemanship,
    "Fear" → Fear,
    "Provoke" → Provoke,
    "Sunburst" → Sunburst,
    "Epic" → Epic,
    "Convoke" → Convoke,
    "Haunt" → Haunt,
    "Split Second" → SplitSecond,
    "Delve" → Delve,
    "Gravestorm" → Gravestorm,
    "Changeling" → Changeling,
    "Hideaway" → Hideaway,
    "Conspire" → Conspire,
    "Persist" → Persist,
    "Wither" → Wither,
    "Retrace" → Retrace,
    "Exalted" → Exalted,
    "Cascade" → Cascade,
    "Rebound" → Rebound,
    "Totem Armor" → TotemArmor,
    "Infect" → Infect,
    "Battle Cry" → BattleCry,
    "Living Weapon" → LivingWeapon,
    "Undying" → Undying,
    "Miracle" → Miracle,
    "Soulbond" → Soulbond,
    "Unleash" → Unleash,
    "Cipher" → Cipher,
    "Evolve" → Evolve,
    "Extort" → Extort,
    "Fuse" → Fuse,
    "Dethrone" → Dethrone,
    "Hidden Agenda" → HiddenAgenda
  )

  def tryParse(text: String): Option[KeywordAbility] =
    nullaryKeywordAbilities.get(text)
}

object RulesTextTokenizer {
  // Too dumb to work generally, but passes all current test cases.
  def tokens(text: String): Seq[String] = for {
    token ← text.split(" +")
  } yield token.trim
}

object RulesTextParser {
  def parse(text: String): ParsedRulesText = {
    val parsedKeywords = RulesTextTokenizer
      .tokens(text)
      .map { ParsedKeywordAbility.tryParse(_) }
      .flatten
      .toVector
    new ParsedRulesText(parsedKeywords)
  }
}
