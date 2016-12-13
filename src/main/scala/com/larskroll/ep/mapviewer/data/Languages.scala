package com.larskroll.ep.mapviewer.data

trait Language {
    def name: String;
}

class LangName(val name: String) extends Language {
    override def toString = name;
}

object Languages {
    object Arabic extends LangName("Arabic")
    object Bengali extends LangName("Bengali")
    object Cantonese extends LangName("Cantonese")
    object Dutch extends LangName("Dutch")
    object English extends LangName("English")
    object Farsi extends LangName("Farsi")
    object French extends LangName("French")
    object German extends LangName("German")
    object Hindi extends LangName("Hindi")
    object Indonesian extends LangName("Indonesian")
    object Italian extends LangName("Italian")
    object Japanese extends LangName("Japanese")
    object Javanese extends LangName("Javanese")
    object Korean  extends LangName("Korean")
    object Mandarin extends LangName("Mandarin")
    object Polish extends LangName("Polish")
    object Portuguese extends LangName("Portuguese")
    object Punjabi extends LangName("Punjabi")
    object Russian extends LangName("Russian")
    object Spanish extends LangName("Spanish")
    object Swahili extends LangName("Swahili")
    object Tamil extends LangName("Tamil")
    object Urdu extends LangName("Urdu")
    object Vietnamese extends LangName("Vietnamese")
    object Wu extends LangName("Wu")
}