import org.scalatest._
import phelddagrif._
import phelddagrif.Mana._

class ManaSpec extends FlatSpec with Matchers {
  "* operator" should "return that many of the given mana type" in {
    (4 * ManaType.Green) should be(Mana(4, ManaType.Green))
    (0 * Colorless) should be(Mana(0, Colorless))
  }
}
