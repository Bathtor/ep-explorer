package com.lkroll.ep.mapviewer.datamodel

trait Industry {
  def name: String;
}

class IndustryName(val name: String) extends Industry {
  override def toString = name;
}
