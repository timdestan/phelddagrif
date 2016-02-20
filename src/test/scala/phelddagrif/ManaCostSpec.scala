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
          ManaCost.Colored(White)
        ).colors should be(Set(White))
      }
      "Mana costs with multiple colors should be all those colors" in {
        ManaCost(
          ManaCost.FixedGeneric(1),
          ManaCost.Colored(White),
          ManaCost.Colored(Green),
          ManaCost.Colored(Blue)
        ).colors should be(Set(White, Green, Blue))
      }
      "Hybrid costs should be all their colors" in {
        ManaCost(
          ManaCost.Hybrid(
            ManaCost.Colored(Black),
            ManaCost.Colored(Blue)
          )
        ).colors should be(Set(Blue, Black))
        ManaCost(
          ManaCost.Hybrid(
            ManaCost.FixedGeneric(4),
            ManaCost.Colored(Red)
          )
        ).colors should be(Set(Red))
      }
      "Phyrexian costs should be their color" in {
        ManaCost(
          ManaCost.Phyrexian(Red)
        ).colors should be(Set(Red))
      }
    }
  }
}
