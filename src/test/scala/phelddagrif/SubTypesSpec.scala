import org.scalatest._
import phelddagrif._
import phelddagrif.LandType._

class SubTypesSpec extends FlatSpec with Matchers {
  "LandType.isBasic" should "return whether type is a basic land type" in {
    Mountain.isBasic should be (true)
    Plains.isBasic should be (true)
    Urzas.isBasic should be (false)
  }
  "LandType.color" should "return corresponding color for basic lands" in {
    Plains.color should be (Some(Color.White))
    Forest.color should be (Some(Color.Green))
  }
  it should "return no color for non-basic land types" in {
    Locus.color should be (None)
    PowerPlant.color should be (None)
  }
}
