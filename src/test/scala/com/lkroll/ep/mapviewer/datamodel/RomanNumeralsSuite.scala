package com.lkroll.ep.mapviewer.datamodel

import org.scalatest.funsuite.AnyFunSuite

class RomanNumeralsSuite extends AnyFunSuite {
  test("moon ordinals use repetition, subtraction and composition") {
    val examples = List(
      3 -> "III",
      4 -> "IV",
      9 -> "IX",
      40 -> "XL",
      90 -> "XC",
      400 -> "CD",
      900 -> "CM",
      44 -> "XLIV"
    )
    for ((ordinal, expected) <- examples) {
      assert(RomanNumerals.toRomanNumerals(ordinal) === expected)
    }
  }
}
