import org.scalatest._
import phelddagrif.game.Player
import phelddagrif.mana.ManaPool

class PlayerSpec extends FlatSpec with Matchers {
  "Player.apply" should "produce a player with 20 life." in {
    Player().life should be (20)
  }
  it should "produce a player with an empty mana pool" in {
    Player().manaPool.mana.isEmpty should be (true)
  }
}
