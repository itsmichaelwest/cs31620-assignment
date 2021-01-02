package uk.ac.aber.dcs.cs31620.phrasepad.model

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import java.util.*

// Represents a language, uses the Android Locale framework
class Language(var locale: Locale) {

    // In the extremely rare event no locale parameter is passed, resort to a default of English
    constructor(): this(Locale.ENGLISH)

    // Override function for use in the SetLanguagesFragment
    override fun toString(): String = getNativeName()

    // Get the "native" name of the language (i.e. the language in its native script)
    private fun getNativeName(): String = locale.getDisplayLanguage(Locale(locale.language)).capitalize(locale)

    // Get the name of the language appropriate to the device's language
    private fun getDeviceLangName(): String = locale.displayLanguage.capitalize(Locale.getDefault())

    fun getPreferredName(context: Context): String {
        return if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("always_dev_lang", false))
            getDeviceLangName()
        else
            getNativeName()
    }

    // Get the flag for that country, using the LocaleHelper
    fun getFlag(context: Context): Drawable? = ContextCompat.getDrawable(context, LocaleFlagHelper.get(locale.isO3Language).flag)

    // Return the ISO 639-3 code for the language
    fun getCode(): String = locale.isO3Language

    fun getCountry(): String = locale.toLanguageTag()
}
