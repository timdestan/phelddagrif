package phelddagrif
package parse

import cats.data.Xor

// A parser that matches a single keyword only.
case class KeywordParser[T](result: T, keyword: String) extends Parser[T] {
  override def error(found: String): Error =
    Error(s"Expected keyword but found $found.")

  def parse(str: String): Error Xor T =
    if (str == keyword)
      Xor.Right(result)
    else
      Xor.Left(error(str))
}
