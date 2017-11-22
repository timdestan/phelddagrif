package phelddagrif
package search

case class Pattern[+T](
    text: String,
    value: T
)

case class Match[+T](
    value: T,
    start: Int
)

class Matcher[+T](patterns: List[Pattern[T]]) {
  def find(text: String): List[Match[T]] =
    for {
      p <- patterns
      i <- 0 to text.size - p.text.size
      if (text.slice(i, i + p.text.size) == p.text)
    } yield Match(p.value, i)
}

object Matcher {
  def apply[T](ps: Pattern[T]*): Matcher[T] = new Matcher[T](ps.toList)
}
