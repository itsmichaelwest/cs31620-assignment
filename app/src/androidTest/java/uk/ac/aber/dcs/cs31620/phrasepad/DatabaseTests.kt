package uk.ac.aber.dcs.cs31620.phrasepad

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository

@RunWith(AndroidJUnit4::class)
class DatabaseTests {

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun checkInsertingPhrase() {

    }
}