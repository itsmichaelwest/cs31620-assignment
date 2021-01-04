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
 * @since 1.0
 */
class Language(var locale: Locale) {

    // In the extremely rare event no locale parameter is passed, resort to a default of English
    constructor() : this(Locale.ENGLISH)

    override fun toString(): String = getNativeName()

    /**
     * Retrieve the language's name in its native script, capitalized if appropriate, using
     * [Locale.getDisplayLanguage].
     * @return The name of the language object in that language. If the locale calls for language
     * names to be capitalized, then the returned string shall be.
     */
    fun getNativeName(): String =
        locale.getDisplayLanguage(Locale(locale.language)).capitalize(locale)


    /**
     * Retrieve the language's name in a format that is appropriate for the user depending on the
     * language of their device, using [Locale.getDisplayLanguage].
     * @return The name of the language in the user's device language. If the user's device language
     * calls for names to be capitalized, then the returned string shall be.
     */
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

    /**
     * Retrieve the flag image specified for the language in [LocaleHelper].
     * @param context [Context]
     * @return The flag image for the language as a [Drawable].
     */
    fun getFlag(context: Context): Drawable? =
        ContextCompat.getDrawable(context, LocaleHelper.get(locale.isO3Language).flag)

    /**
     * Returns a three-letter abbreviation of this locale's language. See [Locale.getISO3Language]
     * for more.
     * @return A three-letter abbreviation of this locale's language.
     */
    fun getCode(): String = locale.isO3Language
}