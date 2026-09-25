package com.lkroll.ep.mapviewer.datamodel

import com.lkroll.ep.mapviewer.three.Vector3

import java.util.{UUID => JUUID, _}

import org.scalajs.dom
import scala.scalajs.js

object ImportGood {
  import ExtraUnits._ // nested relative imports are outside the rule
  import java.util.concurrent.atomic.AtomicInteger // nested unused imports are outside the rule

  val id: JUUID = JUUID.randomUUID()
  def compare: Comparator[String] = Comparator.naturalOrder[String]()
  val jsValue: js.Object = js.Dynamic.literal()
  def position(value: Vector3): Vector3 = value
  def window: dom.Window = dom.window
}
