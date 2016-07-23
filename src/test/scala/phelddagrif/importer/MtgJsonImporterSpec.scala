import cats.data.Xor
import org.scalatest._
import phelddagrif._
import phelddagrif.Color.{ White, Blue, Black, Red, Green }
import phelddagrif.importer._

class MtgJsonImporterSpec extends FreeSpec with Matchers {
  "MtgJsonImporter.importCard" - {
    "should be able to import a card" in {
      MtgJsonImporter.importCard("""
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
""") should be(
        Xor.Right(Card(
          "Air Elemental",
          Vector(CardType.Creature),
          Vector(CreatureType.Elemental),
          ManaCost(
            ManaCost.FixedGeneric(3),
            ManaCost.Blue,
            ManaCost.Blue
          ),
          Vector(Flying)
        ))
      )
    }

    "should fail on bad mana color in mana cost" in {
      MtgJsonImporter.importCard("""
{
  "layout": "normal",
  "name": "Bob Dole",
  "manaCost": "{3}{U}{Q}",
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
""") should be(
        Xor.Left(Error("Expected mana symbol. Found Q"))
      )
    }

    "should be able to import a land" in {
      MtgJsonImporter.importCard("""
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
""") should be(
        Xor.Right(Card(
          "Plateau",
          Vector(CardType.Land),
          Vector(LandType.Mountain, LandType.Plains),
          ManaCost.Zero,
          Vector()
        ))
      )
    }
  }
}
