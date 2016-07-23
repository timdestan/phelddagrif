package phelddagrif
package importer

import cats.data.Xor
import io.circe.generic.auto._
import io.circe.jawn.JawnParser

// Representation of the parts of the MtgJson data that we currently care about.
case class MtgJsonCard(
  name:       String,
  manaCost:   Option[String],
  types:      Vector[String],
  subtypes:   Option[Vector[String]],
  supertypes: Option[Vector[String]],
  text:       Option[String]
)

// Importer to read in Magic card data in the format provided by mtgjson.com
object MtgJsonImporter {
  lazy val parser = new JawnParser()

  def importCard(text: String): Error Xor Card =
    parser.decode[MtgJsonCard](text)
      .leftMap(Error.fromThrowable)
      .flatMap(json ⇒ parseCardParts(json))

  def parseCardParts(json: MtgJsonCard): Error Xor Card = {
    val types = json.types.map { CardTypeParser.tryParse(_) }
      .flatten
      .toVector
    val subtypeStrings = json.subtypes.getOrElse(Vector())
    val subtypes = subtypeStrings
      .map(CardSubtypeParser.tryParse(_))
      .flatten.toVector
    val rulesText =
      json.text.map(RulesTextParser.parse(_))
        .getOrElse(ParsedRulesText.empty)

    ManaCost.parse(json.manaCost.getOrElse(""))
      .map { manaCost ⇒
        Card(
          json.name,
          types,
          subtypes,
          manaCost,
          rulesText.keywordAbilities
        )
      }
  }
}
