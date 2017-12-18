package phelddagrif
package importer

import cats._
import cats.data._
import cats.implicits._

object ParsedKeywordAbility {
  // Map from name to keyword abilities for keywords that have
  // no modifiers.
  val nullaryKeywordAbilities = Map[String, KeywordAbility](
    "Deathtouch"     -> Deathtouch,
    "Defender"       -> Defender,
    "Double Strike"  -> DoubleStrike,
    "First Strike"   -> FirstStrike,
    "Flash"          -> Flash,
    "Flying"         -> Flying,
    "Haste"          -> Haste,
    "Hexproof"       -> Hexproof,
    "Indestructible" -> Indestructible,
    "Intimidate"     -> Intimidate,
    "Lifelink"       -> Lifelink,
    "Reach"          -> Reach,
    "Shroud"         -> Shroud,
    "Trample"        -> Trample,
    "Vigilance"      -> Vigilance,
    "Banding"        -> Banding,
    "Flanking"       -> Flanking,
    "Phasing"        -> Phasing,
    "Shadow"         -> Shadow,
    "Horsemanship"   -> Horsemanship,
    "Fear"           -> Fear,
    "Provoke"        -> Provoke,
    "Sunburst"       -> Sunburst,
    "Epic"           -> Epic,
    "Convoke"        -> Convoke,
    "Haunt"          -> Haunt,
    "Split Second"   -> SplitSecond,
    "Delve"          -> Delve,
    "Gravestorm"     -> Gravestorm,
    "Changeling"     -> Changeling,
    "Hideaway"       -> Hideaway,
    "Conspire"       -> Conspire,
    "Persist"        -> Persist,
    "Wither"         -> Wither,
    "Retrace"        -> Retrace,
    "Exalted"        -> Exalted,
    "Cascade"        -> Cascade,
    "Rebound"        -> Rebound,
    "Totem Armor"    -> TotemArmor,
    "Infect"         -> Infect,
    "Battle Cry"     -> BattleCry,
    "Living Weapon"  -> LivingWeapon,
    "Undying"        -> Undying,
    "Miracle"        -> Miracle,
    "Soulbond"       -> Soulbond,
    "Unleash"        -> Unleash,
    "Cipher"         -> Cipher,
    "Evolve"         -> Evolve,
    "Extort"         -> Extort,
    "Fuse"           -> Fuse,
    "Dethrone"       -> Dethrone,
    "Hidden Agenda"  -> HiddenAgenda
  )

  def tryParse(text: String): Option[KeywordAbility] =
    nullaryKeywordAbilities.get(text)
}

case class RulesText(
    keywordAbilities: Vector[KeywordAbility]
)

object RulesText {
  val empty = RulesText(Vector())

  sealed trait Token {
    import Token._

    override def toString = this match {
      case Word(w)         => s"w(${w})"
      case Punct(p)        => s"p(${p})"
      case ReminderText(r) => s"r(${r})"
      case Ws(ws)          => "<ws>"
    }
  }

  object Token {
    case class Word(text: String)         extends Token
    case class Punct(symbol: String)      extends Token
    case class ReminderText(text: String) extends Token
    case class Ws(ws: String)             extends Token

    import fastparse.all._

    object Parsers {
      // Some of these are non-ascii. TODO: Normalize.
      val punctStr   = "\",.:{}/+-—•−"
      val nonWordStr = punctStr + " \n()"

      val ws    = CharIn(" \n").!.map(Ws(_))
      val word  = CharsWhile(!nonWordStr.contains(_)).rep(min = 1).!.map(Word(_))
      val punct = CharIn(punctStr).!.map(Punct(_))
      val reminderText =
        P("(" ~/ CharsWhile(_ != ')').! ~ ")").map(ReminderText(_))
      val tokens: Parser[Vector[Token]] =
        (ws | reminderText | word | punct).rep.map(_.toVector)
    }
    val parser = P(Parsers.tokens ~ End)
  }

  def tokenize(text: String): Result[Vector[Token]] =
    Token.parser.parse(text).toResult

  object Parser {
    import Token._

    def parse(text: String): Result[RulesText] =
      tokenize(text)
        .map { tokens =>
          tokens.map {
            case Word(w)         => ParsedKeywordAbility.tryParse(w)
            case ReminderText(_) => None
            case Punct(_)        => None
            case Ws(_)           => None
          }.toVector
        }
        .map(ts => RulesText(ts.flatten))
  }
}
