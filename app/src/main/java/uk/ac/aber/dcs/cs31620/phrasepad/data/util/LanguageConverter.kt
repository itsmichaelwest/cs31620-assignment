package uk.ac.aber.dcs.cs31620.phrasepad.data.util

import androidx.room.TypeConverter
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language

object LanguageConverter {
    @TypeConverter
    @JvmStatic
    fun toString(language: Language) = language.toString()

    //@TypeConverter
    //@JvmStatic
    //fun toLanguage(language: String) = Language.valueOf(language)
}