import phelddagrif.search._
import utest._

sealed trait Battlestar
object Galactica extends Battlestar
object Pegasus   extends Battlestar
object Columbia  extends Battlestar

object MatcherSpec extends TestSuite {
  val tests = Tests {
    "Matcher.find" - {
      "empty input" - {
        assert(Matcher(Pattern("a", Galactica)).find("") == Nil)
      }

      "empty pattern" - {
        val m = Matcher(Pattern("", Galactica))
        assert(
          m.find("abc").toSet ==
            Set(
              Match(Galactica, 0),
              Match(Galactica, 1),
              Match(Galactica, 2),
              Match(Galactica, 3)
            ))
        assert(m.find("") == List(Match(Galactica, 0)))
      }

      "simple matches" - {
        val m = Matcher(Pattern("aa", Galactica))
        assert(m.find("aa") == List(Match(Galactica, 0)))
        assert(m.find("baa") == List(Match(Galactica, 1)))
        assert(m.find("a") == Nil)
      }

      "multiple matches from same pattern" - {
        val m = Matcher(
          Pattern("a", Galactica),
          Pattern("aa", Pegasus)
        )
        assert(
          m.find("aaa").toSet ==
            Set(
              Match(Galactica, 0),
              Match(Galactica, 1),
              Match(Galactica, 2),
              Match(Pegasus, 0),
              Match(Pegasus, 1)
            ))
      }

      "more overlapping patterns" - {
        val m = Matcher(Pattern("nana", Galactica))
        assert(
          m.find("nananananananana batman").toSet ==
            Set(
              Match(Galactica, 0),
              Match(Galactica, 2),
              Match(Galactica, 4),
              Match(Galactica, 6),
              Match(Galactica, 8),
              Match(Galactica, 10),
              Match(Galactica, 12)
            ))
      }

      "conflicting matches" - {
        val m = Matcher(
          Pattern("a", Pegasus),
          Pattern("a", Galactica)
        )
        assert(
          m.find("aa").toSet ==
            Set(
              Match(Galactica, 0),
              Match(Galactica, 1),
              Match(Pegasus, 0),
              Match(Pegasus, 1)
            ))
      }
    }
  }
}
