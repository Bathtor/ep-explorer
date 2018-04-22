package com.lkroll.ep.mapviewer.graphics

import com.lkroll.ep.mapviewer.datamodel.{ Settlement => SettlementData, Aerostat => AerostatData, SyncOrbitStation => SyncOrbitStationData, PandoraGate => PandoraGateData, AstronomicalObject, AtmosphericObject => AtmosphericObjectData, Bathyscaphe => BathyscapheData, UndergroundSettlement => USData }

object AtmosphericObject {
  def fromData(ao: AtmosphericObjectData): GraphicsObject = {
    ao match {
      case s: SettlementData       => Settlement.fromData(s)
      case a: AerostatData         => Aerostat.fromData(a)
      case s: SyncOrbitStationData => SyncOrbitStation.fromData(s)
      case b: BathyscapheData      => Bathyscaphe.fromData(b)
      case u: USData               => UndergroundSettlement.fromData(u)
      case p: PandoraGateData      => PandoraGate.fromData(p)
      case _                       => throw new RuntimeException(s"Unkown AtmosphericObject: ${ao}")
    }
  }
}
