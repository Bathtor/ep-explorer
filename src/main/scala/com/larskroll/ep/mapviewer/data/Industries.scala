package com.larskroll.ep.mapviewer.data

trait Industry {
    def name: String;
}

class IndustryName(val name: String) extends Industry {
    override def toString = name;
}

object Industries {
    object Counterfeiting extends IndustryName("Counterfeiting")
    object EcosystemManagement extends IndustryName("Ecosystem Management")
    object EnvironmentalData extends IndustryName("Environmental Data")
    object Information extends IndustryName("Information")
    object MilitaryTech extends IndustryName("Military Tech")
    object Politics extends IndustryName("Politics")
    object Research extends IndustryName("Research")
    object ResortServices extends IndustryName("Resort Services")
    object Salvage extends IndustryName("Salvage")
    object Scavenging extends IndustryName("Scavenging")
    object Shipping extends IndustryName("Shipping")
    object Terraforming extends IndustryName("Terraforming")
    object Tourism extends IndustryName("Tourism")
    object Trade extends IndustryName("Trade")
    object Training extends IndustryName("Training")
    object Transport extends IndustryName("Transport")
    object ZeroGManufacturing extends IndustryName("Zero-G Manufacturing")
}