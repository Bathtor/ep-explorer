package com.lkroll.ep.mapviewer.datamodel

import org.scalatest.funsuite.AnyFunSuite
import OrbitDistance.{Infinite, Path, Similar, Zero}
import OrbitDistance.Step.{Down, Up}

class OrbitDistanceSuite extends AnyFunSuite {
  test("the shortest finite path wins in either candidate order") {
    val short = Path(Up)
    val long = Path(Up, Down, Down)
    assert(OrbitDistance.min(List(long, short)) === short)
    assert(OrbitDistance.min(List(short, long)) === short)
  }

  test("zero takes precedence over similar") {
    assert(OrbitDistance.min(List(Zero, Similar)) === Zero)
    assert(OrbitDistance.min(List(Similar, Zero)) === Zero)
  }

  test("similar takes precedence over a non-empty path") {
    assert(OrbitDistance.min(List(Path(Down), Similar)) === Similar)
    assert(OrbitDistance.min(List(Similar, Path(Down))) === Similar)
  }

  test("no reachable candidate yields infinite distance") {
    assert(OrbitDistance.min(Nil) === Infinite)
    assert(OrbitDistance.min(List(Infinite, Infinite)) === Infinite)
  }
}
