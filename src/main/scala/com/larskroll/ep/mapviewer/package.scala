package com.larskroll.ep

import org.denigma.threejs._

package object mapviewer {
    implicit class ExtObject3D(obj: Object3D) {
        def moveTo(pos: Vector3) {
            obj.position.set(pos.x, pos.y, pos.z);
        }
    }
    implicit class ExtVector3(v: Vector3) {
        def pretty: String = {
            v.toArray().mkString("<", ",", ">");
        }
    }
    
    implicit class ExtVector2(v: Vector2) {
        def pretty: String = {
            v.toArray().mkString("<", ",", ">");
        }
    }
}