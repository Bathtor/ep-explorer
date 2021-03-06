package com.lkroll.ep.mapviewer.data

import com.lkroll.ep.mapviewer.datamodel._

class Corp(val name: String) {
  def asPolity(): Polity = Polities.CorpAsPolity(this);
}

object Corps {
  implicit class SingleCorpAllegiance(val corp: Corp) extends Allegiance {
    override def description = corp.name
  }
  case class PlanetaryConsortium(val corp: Corp) extends Allegiance {
    override def description = s"Planetary Consortium (${corp.name})";
  }
  case class Hypercorp(val corp: Corp) extends Allegiance {
    override def description = s"Hypercorp (${corp.name})";
  }
  object Acumenic extends Corp("Acumenic")
  object ComEx extends Corp("Comet Express")
  object Cognite extends Corp("Cognite")
  object Ecologene extends Corp("Ecologene")
  object EngDilworth extends Corp("Eng/Dilworth")
  object ExoTech extends Corp("ExoTech")
  object Experia extends Corp("Experia")
  object FaJing extends Corp("Fa Jing")
  object Gonin extends Corp("Go-nin")
  object DirectAction extends Corp("Direct Action")
  object JaehonOffworld extends Corp("Jaehon Offworld")
  object MultipleCorps extends Corp("Multiple Corps")
  object OIA extends Corp("Olympus Infrastructure Authority")
  object Omnicor extends Corp("Omnicor")
  object Pathfinder extends Corp("Pathfinder")
  object Prosperity extends Corp("Prosperity Group")
  object RedZone extends Corp("Red Zone")
  object Skinaesthesia extends Corp("Skinaesthesia")
  object StellarIntelligence extends Corp("Stellar Intelligence")
  object Somatek extends Corp("Somatek")
  object TerraGenesis extends Corp("TerraGenesis")
  object TTO extends Corp("Tharsis Terraforming Office")
  object Volkov extends Corp("Volkov")
  object Zrbny extends Corp("Zrbny Limited")
}
