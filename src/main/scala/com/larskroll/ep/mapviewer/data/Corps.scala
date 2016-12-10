package com.larskroll.ep.mapviewer.data

class Corp(val name: String)

object Corps {
    implicit class SingleCorpAllegiance(val corp: Corp) extends Allegiance {
        override def description = corp.name
    }
    case class PlanetaryConsortium(val corp: Corp) extends Allegiance {
        override def description = s"Planetary Consortium (${corp.name})";
    }
    
    object Experia extends Corp("Experia")
    object FaJing extends Corp("Fa Jing")
    object DirectAction extends Corp("Direct Action")
    object OIA extends Corp("Olympus Infrastructure Authority")
    object Omnicor extends Corp("Omnicor")
    object Pathfinder extends Corp("Pathfinder")
    object Somatek extends Corp("Somatek")
    object TerraGenesis extends Corp("TerraGenesis")
    object TTO extends Corp("Tharsis Terraforming Office")
}