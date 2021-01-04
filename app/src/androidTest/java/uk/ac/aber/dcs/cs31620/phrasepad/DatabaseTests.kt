package uk.ac.aber.dcs.cs31620.phrasepad

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadDatabase
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseDao
import java.io.IOException
import java.util.*
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4ClassRunner::class)
class DatabaseTests {
    private lateinit var dao: PhraseDao
    private lateinit var db: PhrasepadDatabase

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), PhrasepadDatabase::class.java)
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()

        dao = db.phrasepadDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertPhrase() {
        val sourceLanguage = Language(Locale.ENGLISH)
        val destinationLanguage = Language(Locale.FRENCH)

        val phrase = Phrase(
            0,
            sourceLanguage.getCode(),
            destinationLanguage.getCode(),
            "Good morning",
            "Bonjour"
        )

        dao.insertSinglePhrase(phrase)
        val result = dao.testGetDestPhraseFromSource("Good morning")

        assertEquals(result.sourcePhrase, phrase.sourcePhrase)
    }

    @Test
    fun insertAndDeletePhrase() {
        val sourceLanguage = Language(Locale.ENGLISH)
        val destinationLanguage = Language(Locale.FRENCH)

        val phrase = Phrase(
            0,
            sourceLanguage.getCode(),
            destinationLanguage.getCode(),
            "Good morning",
            "Bonjour"
        )

        dao.insertSinglePhrase(phrase)
        val result = dao.testGetDestPhraseFromSource("Good morning")

        assertEquals(result.sourcePhrase, phrase.sourcePhrase)

        dao.deletePhrase(phrase)

        val resultNowNull = dao.testGetDestPhraseFromSource("Good morning")
        assertNull(resultNowNull)
    }
}