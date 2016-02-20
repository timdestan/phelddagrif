import cats.data.Xor
import org.scalatest._
import phelddagrif.importer._

object SampleJson {
  val airElemental = """
{
  "layout": "normal",
  "name": "Air Elemental",
  "manaCost": "{3}{U}{U}",
  "cmc": 5,
  "colors": [
    "Blue"
  ],
  "type": "Creature â€” Elemental",
  "types": [
    "Creature"
  ],
  "subtypes": [
    "Elemental"
  ],
  "text": "Flying",
  "power": "4",
  "toughness": "4",
  "imageName": "air elemental",
  "colorIdentity": [
    "U"
  ]
}
"""

  val plateau = """
{
  "layout": "normal",
  "name": "Plateau",
  "type": "Land â€” Mountain Plains",
  "types": [
    "Land"
  ],
  "subtypes": [
    "Mountain",
    "Plains"
  ],
  "text": "({T}: Add {R} or {W} to your mana pool.)",
  "imageName": "plateau",
  "colorIdentity": [
    "R",
    "W"
  ]
}
"""
}

class MtgJsonImporterSpec extends FreeSpec with Matchers {
  "MtgJsonImporter.importCard" - {
    "should be able to import a card" in {
      MtgJsonImporter.importCard(SampleJson.airElemental) should be(
        Xor.Right(MtgJsonCard(
          "Air Elemental",
          Some("{3}{U}{U}"),
          Vector("Creature"),
          Some(Vector("Elemental")),
          None,
          Some("Flying")
        ))
      )
    }

    "should be able to import a land" in {
      MtgJsonImporter.importCard(SampleJson.plateau) should be(
        Xor.Right(MtgJsonCard(
          "Plateau",
          None,
          Vector("Land"),
          Some(Vector("Mountain", "Plains")),
          None,
          Some("({T}: Add {R} or {W} to your mana pool.)")
        ))
      )
    }
  }
}
