package com.lkroll.ep.mapviewer

sealed trait View extends Paramable {
  def label: String;
  override def toParam: (String, String) = ("view" -> label);
}

object View {

  val values: Seq[View] = Seq(System, Single);

  def fromLabel(label: String): Option[View] = {
    values.find(_.label == label)
  }

  case object System extends View {
    val label = "system";
  }
  case object Single extends View {
    val label = "single";
  }
}

