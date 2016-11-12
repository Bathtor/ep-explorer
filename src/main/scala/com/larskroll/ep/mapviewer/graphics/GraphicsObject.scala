package com.larskroll.ep.mapviewer.graphics

import org.denigma.threejs.Vector3

import com.larskroll.ep.mapviewer.SceneContainer
import com.larskroll.ep.mapviewer.data.AstronomicalObject

import squants.Time

trait GraphicsObject {
    def moveTo(pos: Vector3);

    def addToScene(scene: SceneContainer);

    def update(time: Time);

    def children: List[GraphicsObject];

    def name: String;

    def position: Vector3;

    def id: Double;

    def data: Option[AstronomicalObject] = None;
}