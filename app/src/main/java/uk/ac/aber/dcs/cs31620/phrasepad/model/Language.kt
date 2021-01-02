package uk.ac.aber.dcs.cs31620.phrasepad.model

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import java.util.*

// Represents a language, uses the Android Locale framework
class Language(langLocale: Locale) {
    var locale: Locale = langLocale

    // In the extremely rare event no locale parameter is passed, resort to a default of English
    constructor(): this(Locale.ENGLISH)

    // Override function for use in the SetLanguagesFragment
    override fun toString(): String = getNativeName()

    // Get the "native" name of the language (i.e. the language in its native script)
    fun getNativeName(): String = locale.getDisplayLanguage(Locale(locale.language)).capitalize(locale)

    // Get the name of the language appropriate to the device's language
    fun getDeviceLangName(): String = locale.displayLanguage.capitalize(Locale.getDefault())

    // Get the flag for that country, using the LocaleHelper
    fun getFlag(context: Context): Drawable? = ContextCompat.getDrawable(context, LocaleFlagHelper.get(locale.isO3Language).flag)

    // Return the ISO 639-3 code for the language
    fun getCode(): String = locale.isO3Language

    fun getCountry(): String = locale.toLanguageTag()
}
