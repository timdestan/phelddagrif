package phelddagrif
package importer

import cats.data.Xor
import java.io.File
import io.circe.generic.auto._
import io.circe.jawn.JawnParser
import scala.io.Source

// Representation of the parts of the MtgJson data that we currently care about.
case class MtgJsonCard(
    name: String,
    manaCost: Option[String],
    types: Vector[String],
    subtypes: Option[Vector[String]],
    supertypes: Option[Vector[String]],
    text: Option[String]
)

// Importer to read in Magic card data in the format provided by mtgjson.com
object MtgJsonImporter {
  lazy val parser = new JawnParser()

  def importCard(text: String): Error Xor Card =
    parser
      .decode[MtgJsonCard](text)
      .leftMap(Error.fromThrowable)
      .flatMap(json => parseCardParts(json))

  def parseCardParts(json: MtgJsonCard): Error Xor Card = {
    val types = json.types.map { CardTypeParser.tryParse(_) }.flatten.toVector
    val subtypeStrings = json.subtypes.getOrElse(Vector())
    val subtypes =
      subtypeStrings.map(CardSubtypeParser.tryParse(_)).flatten.toVector
    val rulesText =
      json.text.map(RulesTextParser.parse(_)).getOrElse(ParsedRulesText.empty)

    ManaCost.parse(json.manaCost.getOrElse("")).map { manaCost =>
      Card(
          json.name,
          types,
          subtypes,
          manaCost,
          rulesText.keywordAbilities
      )
    }
  }

  def main(args: Array[String]): Unit = {
    val (errors, successes) = new File("resources/mtgjson").listFiles.map {
      Source.fromFile(_).getLines.mkString
    }.map { text =>
      (text, importCard(text))
    }.partition { case (text, parsed) => parsed.isLeft }

    println(s"Finished parsing ${errors.length + successes.length} files.")
    println(s"  Successfully parsed: ${successes.length} files.")
    if (!errors.isEmpty) {
      println(s"  Failed: ${errors.length} files.")
      println("Example failures:")
      errors.take(5)
            .foreach {
              case (text, Xor.Left(error)) => {
                println(s"Text:\n\n$text")
                println(s"Error:\n\n$error.reason\n")
              }
            }
    }
  }
}
