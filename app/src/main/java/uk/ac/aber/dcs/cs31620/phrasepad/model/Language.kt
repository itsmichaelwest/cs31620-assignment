package uk.ac.aber.dcs.cs31620.phrasepad.model

import java.util.*

class Language(langName: String, langLocale: Locale) {
    var name: String = langName
    var locale: Locale = langLocale

    // Empty constructor to create a default object.
    constructor(): this("", Locale.ENGLISH) {}

    override fun toString(): String {
        return "$name $locale"
    }
}
