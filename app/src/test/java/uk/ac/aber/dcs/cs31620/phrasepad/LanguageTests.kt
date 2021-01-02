package uk.ac.aber.dcs.cs31620.phrasepad

import junit.framework.Assert.assertEquals
import org.junit.Test
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.LocaleFlagHelper
import java.util.*

class LanguageTests {
    @Test
    fun createEmptyLanguage() {
        val english = Language()
        assertEquals("eng", english.getCode())
    }

    @Test
    fun createLanguageUsingLocaleFramework() {
        val french = Language(Locale.FRANCE)
        assertEquals("fra", french.getCode())
    }

    @Test
    fun getLanguageNativeName() {
        val french = Language(Locale.FRANCE)
        assertEquals("Français", french.getNativeName())
    }

    @Test
    fun getLanguageEnglishName() {
        val french = Language(Locale.FRANCE)
        assertEquals("French", french.getDeviceLangName())
    }

    @Test
    fun getFlagDrawable() {
        val french = Language(Locale.FRANCE)
        val flag = LocaleFlagHelper.get(french.getCode())
        assertEquals(R.drawable.fr, flag.flag)
    }
}