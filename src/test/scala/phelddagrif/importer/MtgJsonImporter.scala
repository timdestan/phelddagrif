import org.scalatest._
import phelddagrif.importer._

class MtgJsonImporterSpec extends FreeSpec with Matchers {
  "MtgJsonImporter.import" - {
    "should be able to import a card" in {
      MtgJsonImporter.importCard("""
      {"layout":"normal",
       "name":"Air Elemental",
       "manaCost":"{3}{U}{U}",
       "cmc":5,
       "colors":["Blue"],
       "type":"Creature â€” Elemental",
       "types":["Creature"],
       "subtypes":["Elemental"],
       "text":"Flying",
       "power":"4",
       "toughness":"4",
       "imageName":"air elemental",
       "colorIdentity":["U"]
      }
      """) should be(MtgJsonCard(
                       "Air Elemental",
                       "{3}{U}{U}",
                       Vector("Creature"),
                       Some(Vector("Elemental")),
                       None,
                       Some("Flying")))
    }
  }
}
