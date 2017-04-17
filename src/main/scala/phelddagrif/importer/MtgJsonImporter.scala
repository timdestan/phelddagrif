package phelddagrif
package importer

import cats._
import cats.data._
import cats.implicits._
import fastparse.all._
import java.io.File
import java.util.zip.ZipFile
import io.circe.generic.auto._
import io.circe.jawn.JawnParser
import scala.io.Source

object MtgJson {
  case class Card(
      name: String,
      manaCost: Option[String],
      types: Option[Vector[String]],
      subtypes: Option[Vector[String]],
      supertypes: Option[Vector[String]],
      text: Option[String],
      power: Option[String],
      toughness: Option[String]
  )

  case class Set(
      `type`: String,
      cards: Vector[Card]
  )

  type AllSets = Map[String, Set]
}

// Importer to read in Magic card data in the format provided by mtgjson.com
object MtgJsonImporter {
  type Result[A] = Either[Error, A]

  lazy val parser = new JawnParser()

  // Decode helper that maps errors to our Error type.
  def decode[T](text: String)
               (implicit decoder: io.circe.Decoder[T]): Result[T] =
    parser.decode[T](text)(decoder).left.map(Error.fromThrowable(_))

  def importAllSets(allSetsRawJson: String): Result[Vector[Card]] = {
    decode[MtgJson.AllSets](allSetsRawJson)
      .map(allSets =>
        allSets.values
               .filter(set => set.`type` != "un") // Exclude the un sets.
               .flatMap(set => set.cards.map(parseCardParts(_)))
               .toVector
               .sequenceU)
      .flatten // Flatten the 2 Eithers to 1 Either.
  }

  // All the tests are still written against this interface :(
  def importCard(text: String): Result[Card] =
    decode[MtgJson.Card](text).flatMap(parseCardParts(_))

  implicit class EnrichedParser[A](underlying: Parser[A]) {
    private val fullParser = P(underlying ~ End)
    def parseFull(input: String) : Result[A] =
      fullParser.parse(input) match {
        case Parsed.Success(v, _) => Right(v)
        case failure => Left(Error(failure.toString))
      }
  }

  def parseCardParts(json: MtgJson.Card): Result[Card] = {
    val types = json.types.getOrElse(Vector()).map {
      CardTypeParser.tryParse(_)
    }.flatten.toVector
    val subtypeStrings = json.subtypes.getOrElse(Vector())
    val subtypes =
      subtypeStrings.map(CardSubtypeParser.tryParse(_)).flatten.toVector
    val rulesText =
      json.text.map(RulesTextParser.parse(_)).getOrElse(ParsedRulesText.empty)

    // Ugh
    def combine3[A,B,C](ra: Result[A],
                        rb: Result[B],
                        rc: Result[C]): Result[(A,B,C)] =
      for {
        a <- ra
        b <- rb
        c <- rc
      } yield (a,b,c)

    val manaCost = ManaCost.parser.parseFull(json.manaCost.getOrElse(""))
    var power = json.power.traverseU(PowerToughness.parser.parseFull(_))
    var toughness = json.toughness.traverseU(PowerToughness.parser.parseFull(_))

    combine3(manaCost, power, toughness).map {
      case (manaCost, power, toughness) =>
        Card(
          json.name,
          types,
          subtypes,
          manaCost,
          rulesText.keywordAbilities,
          power,
          toughness
        )
      }
    }.left.map(_.mapReason(
        reason => s"Failed to parse ${json}\nReason: $reason"))

  def main(args: Array[String]): Unit = {
    import scala.collection.JavaConverters._
    val root = new ZipFile("resources/mtgjson/AllSets.json.zip")
    val allSetsRawJson =
      root.entries.asScala
        .map(entry => {
          var source =
            Source.fromInputStream(root.getInputStream(entry))("UTF-8")
          val contents = source.getLines.mkString
          source.close()
          contents
        })
        .toList.head

    importAllSets(allSetsRawJson) match {
      case Left(error) => println(s"Parse failed with $error")
      case Right(cards) => println(s"Successfully parsed ${cards.size} cards.")
    }
  }
}
