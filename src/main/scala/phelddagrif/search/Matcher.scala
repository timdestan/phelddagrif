package phelddagrif
package search

case class Pattern[I, +O](
    pattern: IndexedSeq[I],
    value: O
)

case class Match[+T](
    value: T,
    start: Int
)

class Matcher[-I, +O](patterns: List[Pattern[I, O]]) {
  def find(input: IndexedSeq[I]): List[Match[O]] =
    for {
      p <- patterns
      i <- 0 to input.size - p.pattern.size
      if (input.slice(i, i + p.pattern.size) == p.pattern)
    } yield Match(p.value, i)
}

object Matcher {
  def apply[I, O](ps: Pattern[I, O]*): Matcher[I, O] =
    new Matcher[I, O](ps.toList)
}
