package uk.ac.aber.dcs.cs31620.phrasepad

import junit.framework.Assert.assertEquals
import org.junit.Test
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import java.util.*

class LanguageTests {
    @Test
    fun createEmptyModel() {
        val english = Language()
        assertEquals("", english.name)
    }

    @Test
    fun createModelWithNameAndISO6391Code() {
        val english = Language("English", Locale.ENGLISH)
        assertEquals("English", english.name)
        assertEquals(Locale.ENGLISH, english.locale)
    }

    /*
    @Test
    fun createPhrasebookWithTwoLanguages() {
        val phrasebook = Phrasebook(Language("English", Locale.ENGLISH), Language("Welsh", Locale("cy_gb")))
        assertEquals("English", phrasebook.knownLanguage.name)
        assertEquals(Locale("cy_gb"), phrasebook.unknownLanguage.locale)
    }

    @Test
    fun createPhrasebookAddPhrase() {
        val phrasebook = Phrasebook(Language("English", Locale.ENGLISH), Language("Welsh", Locale("cy_gb")))
        phrasebook.addPhrase("morning", "bore da")
        assertEquals("bore da", phrasebook.phrasebook.get("morning"))
    }

    @Test
    fun createPhrasebookGetPhraseProperly() {
        val phrasebook = Phrasebook(Language("English", Locale.ENGLISH), Language("Welsh", Locale("cy_gb")))
        phrasebook.addPhrase("morning", "bore da")
        val phrase = phrasebook.getPhrase("morning")
        assertEquals("bore da", phrase)
    }

    @Test
    fun getUnknownPhraseLanguage() {
        val phrasebook = Phrasebook(Language("English", Locale.ENGLISH), Language("Welsh", Locale("cy_gb")))
        phrasebook.addPhrase("morning", "bore da")

    }
     */
}