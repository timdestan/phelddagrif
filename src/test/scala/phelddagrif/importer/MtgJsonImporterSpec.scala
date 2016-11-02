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
        Xor.Left(Error("Failure(End:1:7 ...\"{Q}\")"))
        // TODO : Error message is crap.
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

    "should be able to parse variable generic mana cost" in {
      MtgJsonImporter.importCard("""
{
  "layout": "normal",
  "name": "Abandon Hope",
  "manaCost": "{X}{1}{B}",
  "cmc": 2,
  "colors": [
    "Black"
  ],
  "type": "Sorcery",
  "types": [
    "Sorcery"
  ],
  "text": "As an additional cost to cast Abandon Hope, discard X cards.\nLook at target opponent's hand and choose X cards from it. That player discards those cards.",
  "imageName": "abandon hope",
  "colorIdentity": [
    "B"
  ]
}
""") should be(
        Xor.Right(Card(
          "Abandon Hope",
          Vector(CardType.Sorcery),
          Vector(),
          ManaCost(ManaCost.VariableGeneric,
                   ManaCost.FixedGeneric(1),
                   ManaCost.Black),
          Vector()
        ))
      )
    }
  }
}
