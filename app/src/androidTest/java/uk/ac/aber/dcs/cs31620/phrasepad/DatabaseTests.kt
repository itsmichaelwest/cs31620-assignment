package uk.ac.aber.dcs.cs31620.phrasepad

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadDatabase
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseDao
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4ClassRunner::class)
class DatabaseTests {
    private lateinit var dao: PhraseDao
    private lateinit var db: PhrasepadDatabase
    private lateinit var viewModel: PhraseViewModel

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PhrasepadDatabase::class.java).build()
        dao = db.phrasepadDao()

        val application = Mockito.mock(Application::class.java)
        viewModel = PhraseViewModel(application)
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


        viewModel.add(phrase)

        assertEquals(viewModel.getDestPhraseFromSource("Good morning"), phrase.sourcePhrase)
    }

    @Test
    fun checkInsertingPhrase() {
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

        val r = dao.getDestPhraseFromSource("Good morning")
    }
}