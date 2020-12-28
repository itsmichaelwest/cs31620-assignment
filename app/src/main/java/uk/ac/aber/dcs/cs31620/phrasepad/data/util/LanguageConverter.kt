package uk.ac.aber.dcs.cs31620.phrasepad.data.util

import androidx.room.TypeConverter
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import java.util.*

object LanguageConverter {
    @TypeConverter
    @JvmStatic
    fun toString(language: Language) = language.toString()

    @TypeConverter
    @JvmStatic
    fun toLanguage(language: String) = convertToLanguage(language)

    fun convertToLanguage(value: String): Language {
        val returnLang = Language(value.split("\\s".toRegex())[0], Locale(value.split("\\s".toRegex())[1]))
        return returnLang
    }
}