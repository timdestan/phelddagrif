package phelddagrif
package parsing

import fastparse.all._

object NaturalNumber {
  val parser: Parser[Int] = P(CharIn('0' to '9').rep(1).!).map(_.toInt)
}

object Integer {
  val parser: Parser[Int] = P("-".!.? ~ NaturalNumber.parser).map {
    case (minus, value) => if (minus.isEmpty) value else -value
  }
}
