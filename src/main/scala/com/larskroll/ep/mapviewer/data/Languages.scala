package com.larskroll.ep.mapviewer.data

trait Language {
    def name: String;
}

class LangName(val name: String) extends Language {
    override def toString = name;
}

object Languages {
    object Bengali extends LangName("Bengali")
    object Cantonese extends LangName("Cantonese")
    object Dutch extends LangName("Dutch")
    object English extends LangName("English")
    object French extends LangName("French")
    object German extends LangName("German")
    object Hindi extends LangName("Hindi")
    object Italian extends LangName("Italian")
    object Japanese extends LangName("Japanese")
    object Mandarin extends LangName("Mandarin")
    object Russian extends LangName("Russian")
    object Tamil extends LangName("Tamil")
    object Vietnamese extends LangName("Vietnamese")
    object Wu extends LangName("Wu")
}