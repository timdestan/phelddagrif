import org.scalatest._
import phelddagrif.search._

sealed trait Battlestar
object Galactica extends Battlestar
object Pegasus   extends Battlestar
object Columbia  extends Battlestar

class MatcherSpec extends FreeSpec with Matchers {
  "Matcher.find" - {
    "empty input" in {
      Matcher(Pattern("a", Galactica)).find("") should equal(Nil)
    }

    "empty pattern" in {
      val m = Matcher(Pattern("", Galactica))
      m.find("abc").toSet should equal(
        Set(
          Match(Galactica, 0),
          Match(Galactica, 1),
          Match(Galactica, 2),
          Match(Galactica, 3)
        ))
      m.find("") should equal(List(Match(Galactica, 0)))
    }

    "simple matches" in {
      val m = Matcher(Pattern("aa", Galactica))
      m.find("aa") should equal(List(Match(Galactica, 0)))
      m.find("baa") should equal(List(Match(Galactica, 1)))
      m.find("a") should equal(Nil)
    }

    "multiple matches from same pattern" in {
      val m = Matcher(
        Pattern("a", Galactica),
        Pattern("aa", Pegasus)
      )
      m.find("aaa").toSet should equal(
        Set(
          Match(Galactica, 0),
          Match(Galactica, 1),
          Match(Galactica, 2),
          Match(Pegasus, 0),
          Match(Pegasus, 1)
        )
      )
    }

    "more overlapping patterns" in {
      val m = Matcher(Pattern("nana", Galactica))
      m.find("nananananananana batman").toSet should equal(
        Set(
          Match(Galactica, 0),
          Match(Galactica, 2),
          Match(Galactica, 4),
          Match(Galactica, 6),
          Match(Galactica, 8),
          Match(Galactica, 10),
          Match(Galactica, 12)
        )
      )
    }

    "conflicting matches" in {
      val m = Matcher(
        Pattern("a", Pegasus),
        Pattern("a", Galactica)
      )
      m.find("aa").toSet should equal(
        Set(
          Match(Galactica, 0),
          Match(Galactica, 1),
          Match(Pegasus, 0),
          Match(Pegasus, 1)
        ))
    }
  }
}
