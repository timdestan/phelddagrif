package phelddagrif
package parse

import cats.data.Xor

// A parser that succeeds if and only if one of the parsers in the provided list
// succeeds. The parsers are considered in order.
class UnionParser[T](parsers: List[Parser[T]]) extends Parser[T] {
  def parse(str: String): Error Xor T = parseOption(str) match {
    case None => Xor.Left(error(str))
    case Some(value) => Xor.Right(value)
  }
  override def parseOption(str: String): Option[T] =
    parsers.map(_.parseOption(str)).flatten.headOption
}
