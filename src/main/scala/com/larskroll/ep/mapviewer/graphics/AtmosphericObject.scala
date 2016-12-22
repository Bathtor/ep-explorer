package com.larskroll.ep.mapviewer.graphics

import com.larskroll.ep.mapviewer.data.{ Settlement => SettlementData, Aerostat => AerostatData, SyncOrbitStation => SyncOrbitStationData, PandoraGate => PandoraGateData, AstronomicalObject, AtmosphericObject => AtmosphericObjectData, Bathyscaphe => BathyscapheData }

object AtmosphericObject {
  def fromData(ao: AtmosphericObjectData): GraphicsObject = {
    ao match {
      case s: SettlementData       => Settlement.fromData(s)
      case a: AerostatData         => Aerostat.fromData(a)
      case s: SyncOrbitStationData => SyncOrbitStation.fromData(s)
      case b: BathyscapheData      => Bathyscaphe.fromData(b)
      case p: PandoraGateData      => PandoraGate.fromData(p)
      case _                       => throw new RuntimeException(s"Unkown AtmosphericObject: ${ao}")
    }
  }
}
