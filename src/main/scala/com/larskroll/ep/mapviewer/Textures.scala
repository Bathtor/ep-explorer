package com.larskroll.ep.mapviewer

import org.denigma.threejs.Texture

object Textures {
  private val textures = scala.collection.mutable.Map.empty[String, Texture];
  
  def apply(key: String): Texture = textures(key);
  def put(key: String, tex: Texture) = textures.put(key, tex);
}