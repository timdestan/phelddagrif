import org.scalatest._
import phelddagrif._
import phelddagrif.Mana._ // Implicits
import phelddagrif.ManaType._

class ManaPoolSpec extends FlatSpec with Matchers {
  "empty mana pool" should "not contain any mana" in {
    ManaPool.empty.mana.isEmpty should be(true)
  }

  "simplify" should "combine like mana types" in {
    ManaPool.empty.add(1 * Green)
      .add(2 * Green)
      .add(3 * Blue)
      .add(5 * Red)
      .add(2 * Blue)
      .simplify should be(
        ManaPool.empty
          .add(3 * Green)
          .add(5 * Blue)
          .add(5 * Red))
  }
}
