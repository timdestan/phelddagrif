import org.scalatest._
import phelddagrif._

class PlayerSpec extends FlatSpec with Matchers {
  "Player.apply" should "produce a player with 20 life." in {
    Player(Nil).life should be(20)
  }
  it should "produce a player with an empty mana pool" in {
    Player(Nil).manaPool.mana.isEmpty should be(true)
  }
}
