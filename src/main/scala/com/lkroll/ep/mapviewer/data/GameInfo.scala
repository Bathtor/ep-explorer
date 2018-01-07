package com.lkroll.ep.mapviewer.data

trait Allegiance {
  def description: String;
}

case class Unaligned(comment: Option[String]) extends Allegiance {
  override def description = comment match {
    case Some(c) => s"None ($c)"
    case None    => "None"
  }
}
object AbandonedToTITANs extends Unaligned(Some("Abandoned or TITAN"))