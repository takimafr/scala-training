package training

object exo02_suffix {

  def rank(i: Int): String = {

    val suffix = i match {
      case 1 => "st"
      case 2 => "nd"
      case 3 => "rd"
      case _ => "th"
    }

    i + suffix
  }                                               //> rank: (i: Int)String

  rank(2)                                         //> res0: String = 2nd
}