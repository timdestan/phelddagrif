package phelddagrif.importer

import spray.json._

// Representation of the parts of the MtgJson data that we currently care about.
case class MtgJsonCard(
  name: String,
  manaCost: Option[String],
  types: Vector[String],
  subtypes: Option[Vector[String]],
  supertypes: Option[Vector[String]],
  text: Option[String]) {}

object MtgJsonCardProtocol extends DefaultJsonProtocol {
  implicit val MtgCardJsonFormat = jsonFormat6(MtgJsonCard)
}
import MtgJsonCardProtocol._

// Importer to read in Magic card data in the format provided by mtgjson.com
object MtgJsonImporter {
  def importCard(json: String): MtgJsonCard =
    json.parseJson.convertTo[MtgJsonCard]
}
