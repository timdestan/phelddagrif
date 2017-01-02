import org.scalatest._
import phelddagrif._
import phelddagrif.ManaCost._
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
        Right(Card(
          "Air Elemental",
          Vector(CardType.Creature),
          Vector(CreatureType.Elemental),
          ManaCost(FixedGeneric(3), U, U),
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
        Left(Error("Failure(End:1:7 ...\"{Q}\")"))
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
        Right(Card(
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
        Right(Card(
          "Abandon Hope",
          Vector(CardType.Sorcery),
          Vector(),
          ManaCost(VariableGeneric("X"),
                   FixedGeneric(1),
                   B),
          Vector()
        ))
      )
    }

    "should be able to parse hybrid mana costs" in {
      MtgJsonImporter.importCard("""
{
  "layout": "normal",
  "name": "Arrows of Justice",
  "manaCost": "{2}{R/W}",
  "cmc": 3,
  "colors": [
    "White",
    "Red"
  ],
  "type": "Instant",
  "types": [
    "Instant"
  ],
  "text": "Arrows of Justice deals 4 damage to target attacking or blocking creature.",
  "imageName": "arrows of justice",
  "colorIdentity": [
    "W",
    "R"
  ]
}
      """) should be(
        Right(Card(
          "Arrows of Justice",
          Vector(CardType.Instant),
          Vector(),
          ManaCost(FixedGeneric(2), R / W),
          Vector()
        ))
      )
    }

    "should be able to parse double digit mana costs" in {
      MtgJsonImporter.importCard("""
{
  "layout": "normal",
  "name": "Autochthon Wurm",
  "manaCost": "{10}{G}{G}{G}{W}{W}",
  "cmc": 15,
  "colors": [
    "White",
    "Green"
  ],
  "type": "Creature — Wurm",
  "types": [
    "Creature"
  ],
  "subtypes": [
    "Wurm"
  ],
  "text": "Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)\nTrample",
  "power": "9",
  "toughness": "14",
  "imageName": "autochthon wurm",
  "colorIdentity": [
    "W",
    "G"
  ]
}
      """) should be(
        Right(Card(
          "Autochthon Wurm",
          Vector(CardType.Creature),
          Vector(CreatureType.Wurm),
          ManaCost(FixedGeneric(10), G, G, G, W, W),
          Vector(Convoke)
        ))
      )
    }

   "should be able to parse Phyrexian mana costs" in {
      MtgJsonImporter.importCard("""
{
  "layout": "normal",
  "name": "Act of Aggression",
  "manaCost": "{3}{R/P}{R/P}",
  "cmc": 5,
  "colors": [
    "Red"
  ],
  "type": "Instant",
  "types": [
    "Instant"
  ],
  "text": "({R/P} can be paid with either {R} or 2 life.)\nGain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.",
  "imageName": "act of aggression",
  "colorIdentity": [
    "R"
  ]
}
      """) should be(
        Right(Card(
          "Act of Aggression",
          Vector(CardType.Instant),
          Vector.empty,
          ManaCost(FixedGeneric(3), R / P, R / P),
          Vector.empty
        ))
      )
    }

       "should be able to parse colorless mana costs" in {
      MtgJsonImporter.importCard("""
{
  "layout": "normal",
  "name": "Deceiver of Form",
  "manaCost": "{6}{C}",
  "cmc": 7,
  "type": "Creature — Eldrazi",
  "types": [
    "Creature"
  ],
  "subtypes": [
    "Eldrazi"
  ],
  "text": "({C} represents colorless mana.)\nAt the beginning of combat on your turn, reveal the top card of your library. If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form become copies of that card until end of turn. You may put that card on the bottom of your library.",
  "power": "8",
  "toughness": "8",
  "imageName": "deceiver of form"
}
      """) should be(
        Right(Card(
          "Deceiver of Form",
          Vector(CardType.Creature),
          Vector(CreatureType.Eldrazi),
          ManaCost(FixedGeneric(6), C),
          Vector.empty
        ))
      )
    }
  }
}
