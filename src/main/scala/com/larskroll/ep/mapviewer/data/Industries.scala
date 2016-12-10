package com.larskroll.ep.mapviewer.data

trait Industry {
    def name: String;
}

class IndustryName(val name: String) extends Industry {
    override def toString = name;
}

object Industries {
    object Agriculture extends IndustryName("Agriculture")
    object AI extends IndustryName("AI")
    object Biodesign extends IndustryName("Biodesign")
    object Brewing extends IndustryName("Brewing")
    object Colonization extends IndustryName("Colonization")
    object Counterfeiting extends IndustryName("Counterfeiting")    
    object Design extends IndustryName("Design")
    object EcosystemManagement extends IndustryName("Ecosystem Management")
    object Entertainment extends IndustryName("Entertainment")
    object EnvironmentalData extends IndustryName("Environmental Data")
    object Fashion extends IndustryName("Fashion")
    object Gatecrashing extends IndustryName("Gatecrashing")
    object GeneticalEngineering extends IndustryName("Genetical Engineering")
    object Finance extends IndustryName("Finance")
    object HeavyIndustry extends IndustryName("Heavy Industry")
    object Information extends IndustryName("Information")
    object Microfacturing extends IndustryName("Microfacturing")
    object MilitaryTech extends IndustryName("Military Tech")
    object Mining extends IndustryName("Mining")
    object NanoTechnology extends IndustryName("Nano Technology")
    //object Nanofabrication extends IndustryName("Nanofabrication")
    object Politics extends IndustryName("Politics")
    object Research extends IndustryName("Research")
    object ResortServices extends IndustryName("Resort Services")
    object Robotics extends IndustryName("Robotics")
    object Salvage extends IndustryName("Salvage")
    object Scavenging extends IndustryName("Scavenging")
    object Shipping extends IndustryName("Shipping")
    object Terraforming extends IndustryName("Terraforming")
    object Tourism extends IndustryName("Tourism")
    object Trade extends IndustryName("Trade")
    object Training extends IndustryName("Training")
    object Transport extends IndustryName("Transport")
    object Uplift extends IndustryName("Uplift")
    object WaterExtraction extends IndustryName("Water Extraction")
    object ZeroGManufacturing extends IndustryName("Zero-G Manufacturing")
}