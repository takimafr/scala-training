package training

object exo10_ternary {

  implicit class Ternary(val bool: Boolean) {

    def ?[A](t: => A) = new {
      def |(f: => A) = if (bool) t else f
    }
  }

  true ? 1 | 2                                    //> res0: Int = 1
}