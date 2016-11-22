import org.scalatest._
import phelddagrif._
import phelddagrif.Color.{ White, Blue, Black, Red, Green }

class ManaCostSpec extends FreeSpec with Matchers {
  "ManaCost" - {
    "colors" - {
      "{0} should be no colors" in {
        ManaCost.Zero.colors shouldBe empty
      }
      "Generic costs should be no color" in {
        ManaCost(ManaCost.FixedGeneric(7)).colors shouldBe empty
        ManaCost(ManaCost.VariableGeneric).colors shouldBe empty
      }
      "Single mana costs should be their own color" in {
        ManaCost(
          ManaCost.White
        ).colors should be(Set(White))
      }
      "Mana costs with multiple colors should be all those colors" in {
        ManaCost(
          ManaCost.FixedGeneric(1),
          ManaCost.White,
          ManaCost.Green,
          ManaCost.Blue
        ).colors should be(Set(White, Green, Blue))
      }
      "Hybrid costs should be all their colors" in {
        ManaCost(
          ManaCost.Hybrid(
            ManaCost.Black,
            ManaCost.Blue
          )
        ).colors should be(Set(Blue, Black))
        ManaCost(
          ManaCost.Hybrid(
            ManaCost.FixedGeneric(4),
            ManaCost.Red
          )
        ).colors should be(Set(Red))
      }
      "Phyrexian costs should be colorless" in {
        ManaCost(ManaCost.Phyrexian).colors should be(Set.empty)
      }
    }
    "Zero" - {
      "Empty list of symbols should equal zero" in {
        ManaCost() should be(ManaCost.Zero)
      }
      "Single FixedGeneric(0) should equal zero" in {
        ManaCost(ManaCost.FixedGeneric(0)) should be(ManaCost.Zero)
      }
    }
  }
}
