package uk.ac.aber.dcs.cs31620.phrasepad.data.util

import androidx.room.TypeConverter
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import java.util.*

object LanguageConverter {
    @TypeConverter
    @JvmStatic
    fun toString(language: Language) = convertToString(language)

    @TypeConverter
    @JvmStatic
    fun toLanguage(language: String): Language {
        return convertToLanguage(language)
    }

    private fun convertToString(value: Language): String = value.locale.isO3Language

    private fun convertToLanguage(value: String): Language {
        return Language(Locale(value))
    }
}