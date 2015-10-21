import org.scalatest._
import phelddagrif.mana.Colorless
import phelddagrif.mana.Mana
import phelddagrif.mana.Mana._
import phelddagrif.mana.ManaType._

class ManaSpec extends FlatSpec with Matchers {
  "* operator" should "return that many of the given mana type" in {
    (4 * Green) should be (Mana(4, Green))
    (0 * Colorless) should be (Mana(0, Colorless))
  }
}
