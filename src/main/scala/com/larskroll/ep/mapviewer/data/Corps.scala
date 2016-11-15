package com.larskroll.ep.mapviewer.data

class Corp(val name: String)

object Corps {
    implicit class SingleCorpAllegiance(val corp: Corp) extends Allegiance {
        override def description = corp.name
    }
    case class PlanetaryConsortium(val corp: Corp) extends Allegiance {
        override def description = s"Planetary Consortium (${corp.name})";
    }
    object TerraGenesis extends Corp("TerraGenesis")
    object DirectAction extends Corp("Direct Action")
}