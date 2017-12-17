import cats.implicits._
import phelddagrif._
import phelddagrif.ManaCost._
import phelddagrif.importer._
import utest._

object MtgJsonImporterTest extends TestSuite {
  def check(json: String, card: Card) = {
    val expected = Right(card)
    val actual   = MtgJsonImporter.importCard(json)
    assert(expected == actual)
  }

  val tests = Tests {
    "simple creature" - {
      check(
        """
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
""",
        Card(
          "Air Elemental",
          Vector(CardType.Creature),
          Vector(CreatureType.Elemental),
          ManaCost(FixedGeneric(3), U, U),
          Vector(Flying),
          PowerToughness(4).some,
          PowerToughness(4).some,
          "Flying"
        )
      )
    }

    "simple land" - {
      check(
        """
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
""",
        Card(
          "Plateau",
          Vector(CardType.Land),
          Vector(LandType.Mountain, LandType.Plains),
          ManaCost.Zero,
          Vector(),
          power = None,
          toughness = None,
          "({T}: Add {R} or {W} to your mana pool.)"
        )
      )
    }

    "variable generic mana cost" - {
      check(
        """
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
""",
        Card(
          "Abandon Hope",
          Vector(CardType.Sorcery),
          Vector(),
          ManaCost(VariableGeneric("X"), FixedGeneric(1), B),
          Vector(),
          power = None,
          toughness = None,
          "As an additional cost to cast Abandon Hope, discard X cards.\nLook at target opponent's hand and choose X cards from it. That player discards those cards."
        )
      )
    }

    "hybrid mana cost" - {
      check(
        """
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
      """,
        Card(
          "Arrows of Justice",
          Vector(CardType.Instant),
          Vector(),
          ManaCost(FixedGeneric(2), R / W),
          Vector(),
          power = None,
          toughness = None,
          "Arrows of Justice deals 4 damage to target attacking or blocking creature."
        )
      )
    }

    "double digit mana cost" - {
      check(
        """
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
      """,
        Card(
          "Autochthon Wurm",
          Vector(CardType.Creature),
          Vector(CreatureType.Wurm),
          ManaCost(FixedGeneric(10), G, G, G, W, W),
          Vector(Convoke, Trample),
          power = PowerToughness(9).some,
          toughness = PowerToughness(14).some,
          "Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)\nTrample"
        )
      )
    }

    "Phyrexian mana cost" - {
      check(
        """
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
      """,
        Card(
          "Act of Aggression",
          Vector(CardType.Instant),
          Vector.empty,
          ManaCost(FixedGeneric(3), R / P, R / P),
          Vector.empty,
          power = None,
          toughness = None,
          "({R/P} can be paid with either {R} or 2 life.)\nGain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn."
        )
      )
    }

    "colorless mana cost" - {
      check(
        """
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
      """,
        Card(
          "Deceiver of Form",
          Vector(CardType.Creature),
          Vector(CreatureType.Eldrazi),
          ManaCost(FixedGeneric(6), C),
          Vector.empty,
          power = PowerToughness(8).some,
          toughness = PowerToughness(8).some,
          "({C} represents colorless mana.)\nAt the beginning of combat on your turn, reveal the top card of your library. If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form become copies of that card until end of turn. You may put that card on the bottom of your library."
        )
      )
    }
  }
}
