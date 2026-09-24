package com.lkroll.ep.mapviewer

import org.scalajs.dom.MouseEvent

/** Handles pointer input and per-frame updates for a scene camera. */
trait CameraControls {
  /** Handles a mouse button press on the controlled element. */
  def onMouseDown(event: MouseEvent): Unit

  /** Handles pointer movement over the controlled element. */
  def onMouseMove(event: MouseEvent): Unit

  /** Handles a mouse button release on the controlled element. */
  def onMouseUp(event: MouseEvent): Unit

  /** Handles a mouse wheel event on the controlled element. */
  def onMouseWheel(event: MouseEvent): Unit

  /** Applies camera changes for the current render frame. */
  def update(): Unit

  /** Reports whether this control is active. */
  def enabled: Boolean
}
