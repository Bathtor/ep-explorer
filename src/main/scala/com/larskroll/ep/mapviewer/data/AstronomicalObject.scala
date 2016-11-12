package com.larskroll.ep.mapviewer.data

import java.util.UUID;

abstract class AstronomicalObject(val name: String, val id: UUID) {
  def `type`: String;
  def description: Option[String] = None;
}