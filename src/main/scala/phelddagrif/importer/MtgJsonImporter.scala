package phelddagrif.importer

import cats.data.Xor
import io.circe.Error
import io.circe.generic.auto._
import io.circe.jawn.JawnParser

// Representation of the parts of the MtgJson data that we currently care about.
case class MtgJsonCard(
  name: String,
  manaCost: Option[String],
  types: Vector[String],
  subtypes: Option[Vector[String]],
  supertypes: Option[Vector[String]],
  text: Option[String]) {}

// Importer to read in Magic card data in the format provided by mtgjson.com
object MtgJsonImporter {
  lazy val parser = new JawnParser()

  def importCard(json: String): Error Xor MtgJsonCard =
    parser.decode[MtgJsonCard](json)
}
