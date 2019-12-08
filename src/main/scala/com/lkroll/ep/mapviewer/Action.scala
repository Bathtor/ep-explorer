package com.lkroll.ep.mapviewer

trait Action {
  def perform(): Unit;
}
trait UndoableAction extends Action {
  def undo(): Unit;
}
