import org.scalatest._
import phelddagrif._
import phelddagrif.ManaType._

class ManaPoolSpec extends FlatSpec with Matchers {
  "empty mana pool" should "not contain any mana" in {
    ManaPool.empty.mana.isEmpty should be(true)
  }

  "simplify" should "combine like mana types" in {
    ManaPool.of(
        (1, Green),
        (2, Green),
        (3, Blue),
        (5, Red),
        (2, Blue)
    ).simplify should be (ManaPool.of(
        (3, Green),
        (5, Blue),
        (5, Red)
    ))
  }
}
