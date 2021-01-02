package uk.ac.aber.dcs.cs31620.phrasepad.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase

class PhrasepadRepository(application: Application) {
    private val phrasepadDao = PhrasepadDatabase.getDatabase(application)!!.phrasepadDao()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insert(phrase: Phrase) {
        coroutineScope.launch(Dispatchers.IO) {
            phrasepadDao.insertSinglePhrase(phrase)
        }
    }

    fun getPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>> =
        phrasepadDao.getPhrases(sourceLang, destLang)

    fun getDestPhraseFromSource(sourcePhrase: String): LiveData<Phrase> =
        phrasepadDao.getDestPhraseFromSource(sourcePhrase)

    fun getFourPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>> =
        phrasepadDao.getFourPhrases(sourceLang, destLang)

    fun deletePhrase(phrase: Phrase) {
        coroutineScope.launch(Dispatchers.IO) {
            phrasepadDao.deletePhrase(phrase)
        }
    }

    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO) {
            phrasepadDao.deleteAll()
        }
    }

    fun deleteSpecificLanguagePair(sourceLang: String, destLang: String) {
        coroutineScope.launch(Dispatchers.IO) {
            phrasepadDao.deleteSpecificLanguagePair(sourceLang, destLang)
        }
    }
}