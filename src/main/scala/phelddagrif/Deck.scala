package phelddagrif

import cats.implicits._
import phelddagrif.parsing.NaturalNumber

case class DecklistEntry(count: Int, name: String) {
  override def toString = s"$count $name"
}

case class Decklist(main: Seq[DecklistEntry],
                    sideboard: Seq[DecklistEntry]) {
  /**
   * Attempt to resolve the decklist to a deck using cards from universe.
   */
  def toDeck(universe: Universe): Result[Deck] = {
    def resolve(entries: Seq[DecklistEntry]): Result[Seq[Card]] =
      entries.flatMap {
        case DecklistEntry(count, name) => List.fill(count)(name)
      }.map { name =>
        universe.resolve(name)
      }.toVector.sequenceU
    for {
      main <- resolve(main)
      sideboard <- resolve(sideboard)
    } yield Deck(main, sideboard)
  }

  /**
   * Combines like card names (adding the counts) so that the decklist
   * is as compact as possible.
   */
  def simplify(): Decklist = {
    def combine(entries: Seq[DecklistEntry]): Seq[DecklistEntry] =
      entries.groupBy(_.name).toSeq.map {
        case (name, entries) => DecklistEntry(entries.map(_.count).sum, name)
      }
    Decklist(combine(main), combine(sideboard))
  }
}

case class Deck(main: Seq[Card], sideboard: Seq[Card])

trait ParserPrinter {
  def parse(text: String): Result[Decklist]
  def print(decklist: Decklist): String
}

object TxtFormat extends ParserPrinter {
  import fastparse.all._

  val wsParser = P(CharsWhile(" \r\n".toSet))
  val cardNameParser: Parser[String] =
      P(CharsWhile(x => x != '\n' && x != '\r', min = 1).!)
  val entryParser: Parser[DecklistEntry] =
    P(wsParser.? ~
      NaturalNumber.parser ~
      wsParser.? ~
      cardNameParser ~
      wsParser.?).map {
      case (count, name) => DecklistEntry(count, name)
    }
  val entriesParser = entryParser.rep
  val decklistParser: Parser[Decklist] =
      P(entriesParser
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
