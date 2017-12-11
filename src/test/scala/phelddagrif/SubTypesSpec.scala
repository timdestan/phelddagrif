import phelddagrif._
import utest._

object SubTypesSpec extends TestSuite {
  val tests = Tests {
    "LandType" - {
      import phelddagrif.LandType._

      "isBasic" - {
        "should return whether type is a basic land type" - {
          assert(Mountain.isBasic)
          assert(Plains.isBasic)
          assert(!Urzas.isBasic)
        }
      }

      "color" - {
        "should return corresponding color for basic lands" - {
          assert(Plains.color == Some(Color.White))
          assert(Forest.color == Some(Color.Green))
        }

        "should return no color for non-basic land types" - {
          assert(Locus.color == None)
          assert(PowerPlant.color == None)
        }
      }
    }
  }
}
