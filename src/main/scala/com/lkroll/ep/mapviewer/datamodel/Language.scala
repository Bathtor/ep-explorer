package com.lkroll.ep.mapviewer.datamodel

trait Language {
  def name: String;
}

class LangName(val name: String) extends Language {
  override def toString = name;
}
