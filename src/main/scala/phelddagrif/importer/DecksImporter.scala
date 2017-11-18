package phelddagrif
package importer

import cats.implicits._
import cats.syntax._
import scala.io.Source

object DecksImporter {
  def parseWeismannTxt: Result[Decklist] = {
    val textDecklist = Source.fromFile("resources/decks/weissman.txt").mkString
    TxtFormat.parse(textDecklist)
  }

  def main(args: Array[String]): Unit = {
    val universe         = MtgJsonImporter.allCardsFromZipPath().map(Universe(_))
    val weissmanDecklist = parseWeismannTxt
    val weissmanDeck = (universe |@| weissmanDecklist).map {
      case (universe, decklist) => decklist.toDeck(universe)
    }.flatten

    weissmanDeck match {
      case Left(error) => println(s"Failed with $error")
      case Right(deck) => println(deck)
    }
  }
}
