import org.scalatest._
import phelddagrif._
import phelddagrif.ManaCost._

class ManaCostSpec extends FreeSpec with Matchers {
  "ManaCost" - {
    "colors" - {
      "{0} should be no colors" in {
        ManaCost.Zero.colors shouldBe empty
      }
      "Generic costs should be no color" in {
        ManaCost(7).colors shouldBe empty
        ManaCost(VariableGeneric("X")).colors shouldBe empty
      }
      "Single mana costs should be their own color" in {
        ManaCost(W).colors should be(Set(Color.White))
      }
      "Mana costs with multiple colors should be all those colors" in {
        ManaCost(1, W, G, U).colors should be(
          Set(Color.White, Color.Green, Color.Blue))
      }
      "Hybrid costs should be all their colors" in {
        ManaCost(B / U).colors should be(Set(Color.Blue, Color.Black))
        ManaCost(R / 2).colors should be(Set(Color.Red))
        // Can't do ManaCost(2 / R) right now.
      }
      "Phyrexian costs should be colorless" in {
        ManaCost(P).colors should be(Set.empty)
      }
    }
    "Zero" - {
      "Empty list of symbols should equal zero" in {
        ManaCost() should be(ManaCost.Zero)
      }
      "Single {0} should equal zero" in {
        ManaCost(0) should be(ManaCost.Zero)
      }
    }
  }
}
