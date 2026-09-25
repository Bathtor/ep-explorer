package com.lkroll.ep.mapviewer.datamodel

import com.lkroll.ep.mapviewer.utils.PosCache
import org.scalatest.funsuite.AnyFunSuite

class OrbitInterpolationSuite extends AnyFunSuite {
  // Four samples divide a full turn into quarters; their values identify each sample.
  private val samples = PosCache.fill(4, i => (i * 90.0, i), circular = true)

  test("exact boundary samples use the appropriate neighbours") {
    assert(samples.neighbours(0.0) === ((0.0, 0), (90.0, 1)))
    assert(samples.neighbours(270.0) === ((270.0, 3), (0.0, 0)))
    assert(OrbitInterpolation.weight(0.0, 0.0, 90.0) === 0.0)
    assert(OrbitInterpolation.weight(270.0, 270.0, 0.0) === 0.0)

    val linearSamples = PosCache.fill(4, i => (i * 90.0, i))
    assert(linearSamples.neighbours(270.0) === ((180.0, 2), (270.0, 3)))
  }

  test("the midpoint between samples uses equal weights") {
    val (lower, upper) = samples.neighbours(45.0)
    assert((lower, upper) === ((0.0, 0), (90.0, 1)))
    assert(OrbitInterpolation.weight(45.0, lower._1, upper._1) === 0.5)
  }

  test("the final quarter interpolates across the circular seam") {
    val (lower, upper) = samples.neighbours(315.0)
    assert((lower, upper) === ((270.0, 3), (0.0, 0)))
    assert(OrbitInterpolation.weight(315.0, lower._1, upper._1) === 0.5)
  }
}
