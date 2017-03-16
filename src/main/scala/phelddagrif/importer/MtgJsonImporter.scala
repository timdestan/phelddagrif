package phelddagrif
package importer

import cats._
import cats.data._
import cats.implicits._
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
        val text = source.getLines.mkString
        source.close()
        importCard(text).left.map { _.mapReason { reason =>
          s"$path\n$text\n$reason"
        }}
    }.toList.separate

    println(s"Finished parsing ${errors.length + successes.length} files.")
    println(s"  Successfully parsed: ${successes.length} files.")
    if (!errors.isEmpty) {
      println(s"  Failed: ${errors.length} files.")
      println("Example failures:")
      errors.take(20).map(_.reason).foreach(println)
    }
  }
}
