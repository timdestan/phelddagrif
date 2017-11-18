package phelddagrif

sealed trait KeywordAbility

case object Deathtouch   extends KeywordAbility
case object Defender     extends KeywordAbility
case object DoubleStrike extends KeywordAbility
// TODO: Enchant
// TODO: Equip
case object FirstStrike    extends KeywordAbility
case object Flash          extends KeywordAbility
case object Flying         extends KeywordAbility
case object Haste          extends KeywordAbility
case object Hexproof       extends KeywordAbility
case object Indestructible extends KeywordAbility
case object Intimidate     extends KeywordAbility
// TODO: Landwalk
case object Lifelink extends KeywordAbility
// TODO: Protection
case object Reach     extends KeywordAbility
case object Shroud    extends KeywordAbility
case object Trample   extends KeywordAbility
case object Vigilance extends KeywordAbility
case object Banding   extends KeywordAbility
// TODO: Bands with other
// TODO: Rampage
// TODO: Cumulative Upkeep
case object Flanking extends KeywordAbility
case object Phasing  extends KeywordAbility
// TODO: Buyback
case object Shadow extends KeywordAbility
// TODO: Cycling
// TODO: Echo
case object Horsemanship extends KeywordAbility
// TODO: Fading
// TODO: Kicker
// TODO: Flashback
// TODO: Madness
case object Fear extends KeywordAbility
// TODO: Morph
// TODO: Amplify
case object Provoke extends KeywordAbility
// TODO: Storm
// TODO: Affinity
// TODO: Entwine
// TODO: Modular
case object Sunburst extends KeywordAbility
// TODO: Bushido
// TODO: Soulshift
// TODO: Splice
// TODO: Offering
// TODO: Ninjutsu
case object Epic    extends KeywordAbility
case object Convoke extends KeywordAbility
// TODO: Dredge
// TODO: Transmute
// TODO: Bloodthirst
case object Haunt extends KeywordAbility
// TODO: Replicate
// TODO: Forecast
// TODO: Graft
// TODO: Recover
// TODO: Ripple
case object SplitSecond extends KeywordAbility
// TODO: Suspend
// TODO: Vanishing
// TODO: Absorb
// TODO: Aura Swap
case object Delve extends KeywordAbility
// TODO: Fortify
// TODO: Frenzy
case object Gravestorm       extends KeywordAbility
case class Poisonous(n: Int) extends KeywordAbility
// TODO: Transfigure
// TODO: Champion
case object Changeling extends KeywordAbility
// TODO: Evoke
case object Hideaway extends KeywordAbility
// TODO: Prowl
// TODO: Reinforce
case object Conspire      extends KeywordAbility
case object Persist       extends KeywordAbility
case object Wither        extends KeywordAbility
case object Retrace       extends KeywordAbility
case class Devour(n: Int) extends KeywordAbility
case object Exalted       extends KeywordAbility
// TODO: Unearth
case object Cascade            extends KeywordAbility
case class Annihilator(n: Int) extends KeywordAbility
// TODO: Level Up
case object Rebound      extends KeywordAbility
case object TotemArmor   extends KeywordAbility
case object Infect       extends KeywordAbility
case object BattleCry    extends KeywordAbility
case object LivingWeapon extends KeywordAbility
case object Undying      extends KeywordAbility
case object Miracle      extends KeywordAbility
case object Soulbond     extends KeywordAbility
// TODO: Overload
// TODO: Scavenge
case object Unleash extends KeywordAbility
case object Cipher  extends KeywordAbility
case object Evolve  extends KeywordAbility
case object Extort  extends KeywordAbility
case object Fuse    extends KeywordAbility
// TODO: Bestow
case class Tribute(n: Int) extends KeywordAbility
case object Dethrone       extends KeywordAbility
case object HiddenAgenda   extends KeywordAbility
