package phelddagrif
package parse

import cats.data.Xor

class UnimplementedParser extends Parser[Any] {
  def parse(str: String) = Xor.Left(error("This parser is not implemented."))
}
