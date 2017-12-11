import phelddagrif._
import utest._

object ManaSpec extends TestSuite {
  val tests = Tests {
    "ManaPool" - {
      import phelddagrif.ManaType._

      "empty mana pool should not contain any mana" - {
        assert(ManaPool.empty.mana.isEmpty)
      }

      "simplify should combine like mana types" - {
        assert(
          ManaPool
            .of(
              (1, Green),
              (2, Green),
              (3, Blue),
              (5, Red),
              (2, Blue)
            )
            .simplify ==
            ManaPool.of(
              (3, Green),
              (5, Blue),
              (5, Red)
            ))
      }
    }

    "ManaCost" - {
      import phelddagrif.ManaCost._

      "colors" - {
        "{0} should be no colors" - {
          assert(ManaCost.Zero.colors.isEmpty)
        }
        "Generic costs should be no color" - {
          assert(ManaCost(7).colors.isEmpty)
          assert(ManaCost(VariableGeneric("X")).colors.isEmpty)
        }
        "Single mana costs should be their own color" - {
          assert(ManaCost(W).colors == Set(Color.White))
        }
        "Mana costs with multiple colors should be all those colors" - {
          assert(
            ManaCost(1, W, G, U).colors ==
              Set(Color.White, Color.Green, Color.Blue))
        }
        "Hybrid costs should be all their colors" - {
          assert(ManaCost(B / U).colors == Set(Color.Blue, Color.Black))
          assert(ManaCost(R / 2).colors == Set(Color.Red))
          // Can't do ManaCost(2 / R) right now.
        }
        "Phyrexian costs should be colorless" - {
          assert(ManaCost(P).colors.isEmpty)
        }
      }
      "Zero" - {
        "Empty list of symbols should equal zero" - {
          assert(ManaCost() == ManaCost.Zero)
        }
        "Single {0} should equal zero" - {
          assert(ManaCost(0) == ManaCost.Zero)
        }
      }
    }
  }
}
