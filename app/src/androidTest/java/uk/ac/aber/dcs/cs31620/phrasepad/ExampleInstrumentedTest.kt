package uk.ac.aber.dcs.cs31620.phrasepad

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadDatabase
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseDao
import java.io.IOException
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("uk.ac.aber.dcs.cs31620.phrasepad", appContext.packageName)
    }

    private lateinit var dao: PhraseDao
    private lateinit var db: PhrasepadDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PhrasepadDatabase::class.java).build()
        dao = db.phrasepadDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun addPhrase() {
        val english = Language("English", Locale.ENGLISH)
        val welsh = Language("Welsh", Locale("cy_gb"))

        val phrase = Phrase(0, english, welsh, "Morning", "Bore da")

        dao.insertSinglePhrase(phrase)

        val returnPhrase = dao.getKnownPhrase("Morning")

        assertNotNull(returnPhrase)
        assertEquals(returnPhrase.knownPhrase, phrase.knownPhrase)
        assertEquals(returnPhrase.unknownPhrase, phrase.unknownPhrase)
    }
}
