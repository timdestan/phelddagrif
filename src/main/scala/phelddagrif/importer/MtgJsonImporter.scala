package phelddagrif
package importer

import fastparse.all._
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

  def importCard(text: String): Either[Error, Card] =
    parser
      .decode[MtgJsonCard](text)
      .left.map(Error.fromThrowable)
      .flatMap(json => parseCardParts(json))

  def parseCardParts(json: MtgJsonCard): Either[Error, Card] = {
    val types = json.types.map { CardTypeParser.tryParse(_) }.flatten.toVector
    val subtypeStrings = json.subtypes.getOrElse(Vector())
    val subtypes =
      subtypeStrings.map(CardSubtypeParser.tryParse(_)).flatten.toVector
    val rulesText =
      json.text.map(RulesTextParser.parse(_)).getOrElse(ParsedRulesText.empty)

    val parser = ManaCost.parser.map { manaCost =>
      Card(
          json.name,
          types,
          subtypes,
          manaCost,
          rulesText.keywordAbilities
      )
    }
    
    P(parser ~ End).parse(json.manaCost.getOrElse("")) match {
      case Parsed.Success(v, _) => Right(v)
      case failure => Left(Error(failure.toString))
    }
  }

  def main(args: Array[String]): Unit = {
    val (errors, successes) = new File("resources/mtgjson").listFiles.map {
      path =>
        val source = Source.fromFile(path)
        val fileContents = source.getLines.mkString
        source.close()
        fileContents
    }.map { text =>
      (text, importCard(text))
    }.partition { case (text, parsed) => parsed.isLeft }

    println(s"Finished parsing ${errors.length + successes.length} files.")
    println(s"  Successfully parsed: ${successes.length} files.")
    if (!errors.isEmpty) {
      println(s"  Failed: ${errors.length} files.")
      println("Example failures:")
      errors.take(5).foreach {
        case (text, Left(error)) => {
          println(s"Text:\n\n$text")
          println(s"Error:\n\n$error.reason\n")
        }
      }
    }
  }
}
