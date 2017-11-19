package phelddagrif

import fastparse.all._

object NumberParsers {
  val natural: Parser[Int] = P(CharIn('0' to '9').rep(1).!).map(_.toInt)
  val integer: Parser[Int] = P("-".!.? ~ natural).map {
    case (minus, value) => if (minus.isEmpty) value else -value
  }
}
