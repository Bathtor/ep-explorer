package com.lkroll.ep.mapviewer.data

import java.util.UUID;

abstract class AstronomicalObject(val name: String, val id: UUID) {
  def `type`: String;
  def description: Option[String] = None;
  def extraInfo: Seq[Tuple2[String, String]] = Seq.empty;
}