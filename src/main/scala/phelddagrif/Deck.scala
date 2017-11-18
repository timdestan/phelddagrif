package phelddagrif

import cats.implicits._
import phelddagrif.parsing.NaturalNumber

case class DecklistEntry(count: Int, name: String) {
  override def toString = s"$count $name"
}

case class Decklist(main: Vector[DecklistEntry],
                    sideboard: Vector[DecklistEntry]) {
  def mainSize = main.map(_.count).sum

  def sideboardSize = sideboard.map(_.count).sum

  /** Attempt to resolve the decklist to a deck using cards from universe. */
  def toDeck(universe: Universe): Result[Deck] = {
    def resolve(entries: Vector[DecklistEntry]): Result[Vector[Card]] =
      entries
        .flatMap {
          case DecklistEntry(count, name) => List.fill(count)(name).toVector
        }
        .map { name =>
          universe.resolve(name)
        }
        .toVector
        .sequence
    for {
      main      <- resolve(main)
      sideboard <- resolve(sideboard)
    } yield Deck(main, sideboard)
  }
}

object Decklist {

  /** Attempts to parse a decklist from text format. */
  def parse(str: String): Result[Decklist] = TxtFormat.parse(str)

  object TxtFormat extends ParserPrinter {
    import fastparse.all._

    val wsParser = P(CharsWhile(" \r\n".toSet))
    val cardNameParser: Parser[String] =
      P(CharsWhile(x => x != '\n' && x != '\r', min = 1).!)
    val entryParser: Parser[DecklistEntry] =
      P(
        wsParser.? ~
          NaturalNumber.parser ~
          wsParser.? ~
          cardNameParser ~
          wsParser.?).map {
        case (count, name) => DecklistEntry(count, name)
      }
    val entriesParser = entryParser.rep.map(_.toVector)
    val decklistParser: Parser[Decklist] =
      P(
        entriesParser
          ~ wsParser.?
          ~ "Sideboard"
          ~ wsParser.?
          ~ entriesParser).map {
        case (main, sideboard) => Decklist(main, sideboard)
      }

    def parse(text: String) = P(decklistParser ~ End).parse(text).toResult

    def print(decklist: Decklist) =
      (decklist.main.map(_.toString)
        ++ "Sideboard"
        ++ decklist.sideboard.map(_.toString)).mkString("\n")
  }
}

case class Deck(main: Vector[Card], sideboard: Vector[Card]) {
  override def toString = {
    def condense(cards: Vector[Card]): Vector[String] =
      cards
        .groupBy(_.name)
        .toVector
        .map {
          case (name, cards) => s"${cards.size} ${cards.head}"
        }
        .toVector

    condense(main).mkString("\n") + "\nSideboard\n" +
      condense(sideboard).mkString("\n")
  }
}

trait ParserPrinter {
  def parse(text: String): Result[Decklist]
  def print(decklist: Decklist): String
}

object Deck {
  val empty = Deck(Vector(), Vector())
}
