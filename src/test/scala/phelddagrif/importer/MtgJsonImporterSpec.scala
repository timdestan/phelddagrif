import cats.data.Xor
import org.scalatest._
import phelddagrif._
import phelddagrif.Color.{ White, Blue, Black, Red, Green }
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
        Xor.Right(Card(
          "Air Elemental",
          Vector(CardType.Creature),
          Vector(CreatureType.Elemental),
          ManaCost.of(
            ManaCost.FixedGeneric(3),
            ManaCost.Blue,
            ManaCost.Blue
          ),
          Vector(Flying)
        ))
      )
    }

    "should be able to import a land" in {
      MtgJsonImporter.importCard(SampleJson.plateau) should be(
        Xor.Right(Card(
          "Plateau",
          Vector(CardType.Land),
          Vector(LandType.Mountain, LandType.Plains),
          // Should be able to just say Zero here, but c'est la vie.
          ManaCost(Vector()),
          Vector()
        ))
      )
    }
  }
}
