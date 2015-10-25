package phelddagrif

// 205.2a The card types are artifact, creature, enchantment, instant, land,
//        phenomenon, plane, planeswalker, scheme, sorcery, tribal, and
//        vanguard. See section 3, “Card Types.”

// We don't cover some of these at the moment, as they only exist in casual
// game types.

object CardType extends Enumeration {
  type CardType = Value
  val Artifact, Creature, Enchantment, Instant,
      Land, Planeswalker, Sorcery, Tribal = Value
}
