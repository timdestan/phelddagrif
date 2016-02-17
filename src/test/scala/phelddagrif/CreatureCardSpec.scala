import org.scalatest._
import phelddagrif._
import phelddagrif.Subtype._

class CreatureCardSpec extends FlatSpec with Matchers {
  "Creature cards" should "have creature card type" in {
    val bird =
      new CreatureCard("Birds of Paradise",
        ManaCost(ManaCost.Colored(Color.Green)),
        Seq(CreatureType.Bird.asSubtype), /* abilities: */ Seq(), 0, 1)
    bird.cardType should be(Seq(CardType.Creature))
  }
}
