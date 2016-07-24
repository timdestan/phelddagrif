package phelddagrif
package parse

import cats.data.Xor

abstract trait Parser[+A] { self =>
  def parse(str: String): Error Xor A
  def parseOption(str: String): Option[A] = parse(str).toOption
  def error(found: String): Error = Error(s"Found unexpected $found.")

  def map[B](f: A => B): Parser[B] = new Parser[B] {
    def parse(str: String): Error Xor B = self.parse(str).map(f)
    override def parseOption(str: String): Option[B] =
      self.parseOption(str).map(f)
  }
}
