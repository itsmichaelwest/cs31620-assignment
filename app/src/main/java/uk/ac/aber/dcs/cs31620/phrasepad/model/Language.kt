package uk.ac.aber.dcs.cs31620.phrasepad.model

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import java.util.*

/**
 * Represents a language in the app. [Language] makes heavy use of the [Locale] framework to
 * provide names, language codes, and flag drawables.
 *
 * @param locale A [Locale] object. See documentation for information on initialization.
 */
class Language(var locale: Locale) {

    // In the extremely rare event no locale parameter is passed, resort to a default of English
    constructor() : this(Locale.ENGLISH)

    override fun toString(): String = getNativeName()

    fun getNativeName(): String =
        locale.getDisplayLanguage(Locale(locale.language)).capitalize(locale)

    fun getDeviceLangName(): String = locale.displayLanguage.capitalize(Locale.getDefault())

    /**
     * Does the heavy lifting of deciding whether to retrieve the actual native or device native
     * language name. Reads from [PreferenceManager.getDefaultSharedPreferences]. Will call either
     * [getNativeName] or [getDeviceLangName] dependent on the boolean retrieved.
     * @param context [Context]
     * @return The name of the language, dependent on the display preference set by the user.
     */
    fun getPreferredName(context: Context): String {
        return if (PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("always_dev_lang", false)
        )
            getDeviceLangName()
        else
            getNativeName()
    }

    fun getFlag(context: Context): Drawable? =
        ContextCompat.getDrawable(context, LocaleHelper.get(locale.isO3Language).flag)

    fun getCode(): String = locale.isO3Language
}