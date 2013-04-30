package training

import scala.annotation.tailrec

object exo01_rational {

  implicit def int2rational(i: Int) = new Rational(i, 1)
                                                  //> int2rational: (i: Int)training.exo01_rational.Rational
  
  class Rational(n: Int, d: Int) {

    require(d != 0, "denom must be non zero")

    private val g = gcd(n, d)
    val num = n / g
    val denom = d / g

    @tailrec
    private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

    override def toString: String = if (denom == 1) num.toString else s"$num/$denom"

    def +(other: Rational) = new Rational(num * other.denom + other.num * denom, denom * other.denom)
    //def +(other: Int) = new Rational(num + other * denom, denom)

    def *(other: Rational) = new Rational(num * other.num, denom * other.denom)
  }

  new Rational(3, 2).toString                     //> res0: String = 3/2

  new Rational(3, 1) + new Rational(3, 2)         //> res1: training.exo01_rational.Rational = 9/2

  new Rational(3, 1) * new Rational(3, 2)         //> res2: training.exo01_rational.Rational = 9/2

  new Rational(3, 2) + 3                          //> res3: training.exo01_rational.Rational = 9/2

  3 + new Rational(3, 2)                          //> res4: training.exo01_rational.Rational = 9/2
}