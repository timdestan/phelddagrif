package phelddagrif

sealed trait Subtype

object Subtype {
  case class Creature(subtype: CreatureType) extends Subtype
  case class Spell(subtype: SpellType) extends Subtype
  case class Enchantment(subtype: EnchantmentType) extends Subtype
  case class Land(subtype: LandType) extends Subtype
  case class Artifact(subtype: ArtifactType) extends Subtype
  case class Planeswalker(subtype: PlaneswalkerType) extends Subtype
}

sealed trait CreatureType {
  def asSubtype:Subtype = Subtype.Creature(this)
}

object CreatureType {
  case object Advisor extends CreatureType
  case object Ally extends CreatureType
  case object Angel extends CreatureType
  case object Anteater extends CreatureType
  case object Antelope extends CreatureType
  case object Ape extends CreatureType
  case object Archer extends CreatureType
  case object Archon extends CreatureType
  case object Artificer extends CreatureType
  case object Assassin extends CreatureType
  case object AssemblyWorker extends CreatureType
  case object Atog extends CreatureType
  case object Aurochs extends CreatureType
  case object Avatar extends CreatureType
  case object Badger extends CreatureType
  case object Barbarian extends CreatureType
  case object Basilisk extends CreatureType
  case object Bat extends CreatureType
  case object Bear extends CreatureType
  case object Beast extends CreatureType
  case object Beeble extends CreatureType
  case object Berserker extends CreatureType
  case object Bird extends CreatureType
  case object Blinkmoth extends CreatureType
  case object Boar extends CreatureType
  case object Bringer extends CreatureType
  case object Brushwagg extends CreatureType
  case object Camarid extends CreatureType
  case object Camel extends CreatureType
  case object Caribou extends CreatureType
  case object Carrier extends CreatureType
  case object Cat extends CreatureType
  case object Centaur extends CreatureType
  case object Cephalid extends CreatureType
  case object Chimera extends CreatureType
  case object Citizen extends CreatureType
  case object Cleric extends CreatureType
  case object Cockatrice extends CreatureType
  case object Construct extends CreatureType
  case object Coward extends CreatureType
  case object Crab extends CreatureType
  case object Crocodile extends CreatureType
  case object Cyclops extends CreatureType
  case object Dauthi extends CreatureType
  case object Demon extends CreatureType
  case object Deserter extends CreatureType
  case object Devil extends CreatureType
  case object Djinn extends CreatureType
  case object Dragon extends CreatureType
  case object Drake extends CreatureType
  case object Dreadnought extends CreatureType
  case object Drone extends CreatureType
  case object Druid extends CreatureType
  case object Dryad extends CreatureType
  case object Dwarf extends CreatureType
  case object Efreet extends CreatureType
  case object Elder extends CreatureType
  case object Eldrazi extends CreatureType
  case object Elemental extends CreatureType
  case object Elephant extends CreatureType
  case object Elf extends CreatureType
  case object Elk extends CreatureType
  case object Eye extends CreatureType
  case object Faerie extends CreatureType
  case object Ferret extends CreatureType
  case object Fish extends CreatureType
  case object Flagbearer extends CreatureType
  case object Fox extends CreatureType
  case object Frog extends CreatureType
  case object Fungus extends CreatureType
  case object Gargoyle extends CreatureType
  case object Germ extends CreatureType
  case object Giant extends CreatureType
  case object Gnome extends CreatureType
  case object Goat extends CreatureType
  case object Goblin extends CreatureType
  case object God extends CreatureType
  case object Golem extends CreatureType
  case object Gorgon extends CreatureType
  case object Graveborn extends CreatureType
  case object Gremlin extends CreatureType
  case object Griffin extends CreatureType
  case object Hag extends CreatureType
  case object Harpy extends CreatureType
  case object Hellion extends CreatureType
  case object Hippo extends CreatureType
  case object Hippogriff extends CreatureType
  case object Homarid extends CreatureType
  case object Homunculus extends CreatureType
  case object Horror extends CreatureType
  case object Horse extends CreatureType
  case object Hound extends CreatureType
  case object Human extends CreatureType
  case object Hydra extends CreatureType
  case object Hyena extends CreatureType
  case object Illusion extends CreatureType
  case object Imp extends CreatureType
  case object Incarnation extends CreatureType
  case object Insect extends CreatureType
  case object Jellyfish extends CreatureType
  case object Juggernaut extends CreatureType
  case object Kavu extends CreatureType
  case object Kirin extends CreatureType
  case object Kithkin extends CreatureType
  case object Knight extends CreatureType
  case object Kobold extends CreatureType
  case object Kor extends CreatureType
  case object Kraken extends CreatureType
  case object Lamia extends CreatureType
  case object Lammasu extends CreatureType
  case object Leech extends CreatureType
  case object Leviathan extends CreatureType
  case object Lhurgoyf extends CreatureType
  case object Licid extends CreatureType
  case object Lizard extends CreatureType
  case object Manticore extends CreatureType
  case object Masticore extends CreatureType
  case object Mercenary extends CreatureType
  case object Merfolk extends CreatureType
  case object Metathran extends CreatureType
  case object Minion extends CreatureType
  case object Minotaur extends CreatureType
  case object Monger extends CreatureType
  case object Mongoose extends CreatureType
  case object Monk extends CreatureType
  case object Moonfolk extends CreatureType
  case object Mutant extends CreatureType
  case object Myr extends CreatureType
  case object Mystic extends CreatureType
  case object Nautilus extends CreatureType
  case object Nephilim extends CreatureType
  case object Nightmare extends CreatureType
  case object Nightstalker extends CreatureType
  case object Ninja extends CreatureType
  case object Noggle extends CreatureType
  case object Nomad extends CreatureType
  case object Nymph extends CreatureType
  case object Octopus extends CreatureType
  case object Ogre extends CreatureType
  case object Ooze extends CreatureType
  case object Orb extends CreatureType
  case object Orc extends CreatureType
  case object Orgg extends CreatureType
  case object Ouphe extends CreatureType
  case object Ox extends CreatureType
  case object Oyster extends CreatureType
  case object Pegasus extends CreatureType
  case object Pentavite extends CreatureType
  case object Pest extends CreatureType
  case object Phelddagrif extends CreatureType
  case object Phoenix extends CreatureType
  case object Pincher extends CreatureType
  case object Pirate extends CreatureType
  case object Plant extends CreatureType
  case object Praetor extends CreatureType
  case object Prism extends CreatureType
  case object Rabbit extends CreatureType
  case object Rat extends CreatureType
  case object Rebel extends CreatureType
  case object Reflection extends CreatureType
  case object Rhino extends CreatureType
  case object Rigger extends CreatureType
  case object Rogue extends CreatureType
  case object Sable extends CreatureType
  case object Salamander extends CreatureType
  case object Samurai extends CreatureType
  case object Sand extends CreatureType
  case object Saproling extends CreatureType
  case object Satyr extends CreatureType
  case object Scarecrow extends CreatureType
  case object Scorpion extends CreatureType
  case object Scout extends CreatureType
  case object Serf extends CreatureType
  case object Serpent extends CreatureType
  case object Shade extends CreatureType
  case object Shaman extends CreatureType
  case object Shapeshifter extends CreatureType
  case object Sheep extends CreatureType
  case object Siren extends CreatureType
  case object Skeleton extends CreatureType
  case object Slith extends CreatureType
  case object Sliver extends CreatureType
  case object Slug extends CreatureType
  case object Snake extends CreatureType
  case object Soldier extends CreatureType
  case object Soltari extends CreatureType
  case object Spawn extends CreatureType
  case object Specter extends CreatureType
  case object Spellshaper extends CreatureType
  case object Sphinx extends CreatureType
  case object Spider extends CreatureType
  case object Spike extends CreatureType
  case object Spirit extends CreatureType
  case object Splinter extends CreatureType
  case object Sponge extends CreatureType
  case object Squid extends CreatureType
  case object Squirrel extends CreatureType
  case object Starfish extends CreatureType
  case object Surrakar extends CreatureType
  case object Survivor extends CreatureType
  case object Tetravite extends CreatureType
  case object Thalakos extends CreatureType
  case object Thopter extends CreatureType
  case object Thrull extends CreatureType
  case object Treefolk extends CreatureType
  case object Triskelavite extends CreatureType
  case object Troll extends CreatureType
  case object Turtle extends CreatureType
  case object Unicorn extends CreatureType
  case object Vampire extends CreatureType
  case object Vedalken extends CreatureType
  case object Viashino extends CreatureType
  case object Volver extends CreatureType
  case object Wall extends CreatureType
  case object Warrior extends CreatureType
  case object Weird extends CreatureType
  case object Werewolf extends CreatureType
  case object Whale extends CreatureType
  case object Wizard extends CreatureType
  case object Wolf extends CreatureType
  case object Wolverine extends CreatureType
  case object Wombat extends CreatureType
  case object Worm extends CreatureType
  case object Wraith extends CreatureType
  case object Wurm extends CreatureType
  case object Yeti extends CreatureType
  case object Zombie extends CreatureType
  case object Zubera extends CreatureType
}

sealed trait SpellType {
  def asSubtype:Subtype = Subtype.Spell(this)
}

object SpellType {
  case object Arcane extends SpellType
  case object Trap extends SpellType
}

sealed trait EnchantmentType {
  def asSubtype:Subtype = Subtype.Enchantment(this)
}

object EnchantmentType {
  case object Aura extends EnchantmentType
  case object Curse extends EnchantmentType
  case object Shrine extends EnchantmentType
}

sealed trait LandType {
  import LandType._

  def asSubtype:Subtype = Subtype.Land(this)

  // Whether the land type is one of the five basic land types.
  def isBasic: Boolean = this match {
    case Forest => true
    case Mountain => true
    case Swamp => true
    case Island => true
    case Plains => true
    case _ => false
  }

  // Get color if this is a basic land type.
  def color: Option[Color] = this match {
    case Forest => Some(Color.Green)
    case Mountain => Some(Color.Red)
    case Swamp => Some(Color.Black)
    case Island => Some(Color.Blue)
    case Plains => Some(Color.White)
    case _ => None
  }
}

object LandType {
  case object Forest extends LandType
  case object Mountain extends LandType
  case object Swamp extends LandType
  case object Island extends LandType
  case object Plains extends LandType

  case object Urzas extends LandType
  case object Mine extends LandType
  case object PowerPlant extends LandType
  case object Tower extends LandType

  case object Desert extends LandType
  case object Gate extends LandType
  case object Lair extends LandType
  case object Locus extends LandType
}

sealed trait ArtifactType {
  def asSubtype:Subtype = Subtype.Artifact(this)
}

object ArtifactType {
  case object Contraption extends ArtifactType
  case object Equipment extends ArtifactType
  case object Fortification extends ArtifactType
}

sealed trait PlaneswalkerType {
  def asSubtype:Subtype = Subtype.Planeswalker(this)
}

object PlaneswalkerType {
  case object Ajani extends PlaneswalkerType
  case object Ashiok extends PlaneswalkerType
  case object Bolas extends PlaneswalkerType
  case object Chandra extends PlaneswalkerType
  case object Dack extends PlaneswalkerType
  case object Domri extends PlaneswalkerType
  case object Elspeth extends PlaneswalkerType
  case object Garruk extends PlaneswalkerType
  case object Gideon extends PlaneswalkerType
  case object Jace extends PlaneswalkerType
  case object Karn extends PlaneswalkerType
  case object Kiora extends PlaneswalkerType
  case object Koth extends PlaneswalkerType
  case object Liliana extends PlaneswalkerType
  case object Nissa extends PlaneswalkerType
  case object Ral extends PlaneswalkerType
  case object Sarkhan extends PlaneswalkerType
  case object Sorin extends PlaneswalkerType
  case object Tamiyo extends PlaneswalkerType
  case object Tezzeret extends PlaneswalkerType
  case object Tibalt extends PlaneswalkerType
  case object Venser extends PlaneswalkerType
  case object Vraska extends PlaneswalkerType
  case object Xenagos extends PlaneswalkerType
}
