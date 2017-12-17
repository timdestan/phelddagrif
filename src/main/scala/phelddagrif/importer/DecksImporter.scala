package phelddagrif
package importer

import cats.implicits._
import cats.syntax._
import scala.io.Source

object DecksImporter {
  def parseWeismannTxt: Result[Decklist] =
    Decklist.parse(Source.fromFile("resources/decks/weissman.txt").mkString)

  def main(args: Array[String]): Unit = {
    val universe         = MtgJsonImporter.importAllSets.map(Universe(_))
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
