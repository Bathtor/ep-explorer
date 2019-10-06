package com.lkroll.ep.mapviewer.datamodel

object RomanNumerals {
  def toRomanNumerals(number: Int): String = {
    toRomanNumerals(number,
                    List(("M", 1000),
                         ("CM", 900),
                         ("D", 500),
                         ("CD", 400),
                         ("C", 100),
                         ("XC", 90),
                         ("L", 50),
                         ("XL", 40),
                         ("X", 10),
                         ("IX", 9),
                         ("V", 5),
                         ("IV", 4),
                         ("I", 1)))
  }

  private def toRomanNumerals(number: Int, digits: List[(String, Int)]): String = digits match {
    case Nil    => ""
    case h :: t => h._1 * (number / h._2) + toRomanNumerals(number % h._2, t)
  }

}
