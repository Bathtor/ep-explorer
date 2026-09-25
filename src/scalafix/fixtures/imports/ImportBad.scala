package com.lkroll.ep.mapviewer.datamodel

import scala.scalajs.js
import java.util.{UUID, Comparator}
import org.scalajs.dom
import OrbitDistance.step2dist

object ImportBad {
  import ExtraUnits._ // this nested relative import must not be reported
  import java.util.concurrent.atomic.AtomicInteger // this nested unused import must not be reported

  val value: js.Object = js.Dynamic.literal()
  val id: UUID = UUID.randomUUID()
  def window: dom.Window = dom.window
}
