package com.larskroll.ep.mapviewer.data

trait Language {
  def name: String;
}

class LangName(val name: String) extends Language {
  override def toString = name;
}

object AnyLang extends Language {
  def name: String = "Any";
  override def toString = name;
}

object Languages {
  object Arabic extends LangName("Arabic")
  object Bengali extends LangName("Bengali")
  object Burmese extends LangName("Burmese")
  object Cantonese extends LangName("Cantonese")
  object Dutch extends LangName("Dutch")
  object English extends LangName("English")
  object Farsi extends LangName("Farsi")
  object Finnish extends LangName("Finnish")
  object French extends LangName("French")
  object German extends LangName("German")
  object Greek extends LangName("Greek")
  object Hebrew extends LangName("Hebrew")
  object Hindi extends LangName("Hindi")
  object Indonesian extends LangName("Indonesian")
  object Italian extends LangName("Italian")
  object Japanese extends LangName("Japanese")
  object Javanese extends LangName("Javanese")
  object Korean extends LangName("Korean")
  object Kurdish extends LangName("Kurdish")
  object Latin extends LangName("Latin")
  object Mandarin extends LangName("Mandarin")
  object Malay extends LangName("Malay")
  object Polish extends LangName("Polish")
  object Portuguese extends LangName("Portuguese")
  object Punjabi extends LangName("Punjabi")
  object Russian extends LangName("Russian")
  object Skandinaviska extends LangName("Skandinaviska")
  object Serbian extends LangName("Serbian")
  object Spanish extends LangName("Spanish")
  object Suryan extends LangName("Suryan")
  object Swahili extends LangName("Swahili")
  object Tagalog extends LangName("Tagalog")
  object Tamil extends LangName("Tamil")
  object Thai extends LangName("Thai")
  object Turkish extends LangName("Turkish")
  object Urdu extends LangName("Urdu")
  object Vietnamese extends LangName("Vietnamese")
  object Wu extends LangName("Wu")
}
