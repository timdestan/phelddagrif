package phelddagrif

// 205.2a The card types are artifact, creature, enchantment, instant, land,
//        phenomenon, plane, planeswalker, scheme, sorcery, tribal, and
//        vanguard. See section 3, “Card Types.”

// We don't cover some of these at the moment, as they only exist in casual
// game types.

sealed trait CardType

object CardType {
  case object Artifact     extends CardType
  case object Creature     extends CardType
  case object Enchantment  extends CardType
  case object Instant      extends CardType
  case object Land         extends CardType
  case object Planeswalker extends CardType
  case object Sorcery      extends CardType
  case object Tribal       extends CardType
}
