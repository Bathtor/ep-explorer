package com.lkroll.ep.mapviewer

import squants.Time

trait TimeAnimatedScene {
  def start(): Unit;
  def step(): Unit;
  def stop(): Unit;
  def setSpeed(t: Time): Unit;
  def setOffset(t: Time): Unit;
}

trait Selecting {
  def select(obj: graphics.GraphicsObject): Unit;
}

trait Tracking {
  def track(obj: graphics.GraphicsObject); Unit;
}