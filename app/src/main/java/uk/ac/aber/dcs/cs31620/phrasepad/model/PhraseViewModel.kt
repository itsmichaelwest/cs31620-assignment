package uk.ac.aber.dcs.cs31620.phrasepad.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository

class PhraseViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PhrasepadRepository = PhrasepadRepository(application)

    fun getPhrases(sourceLang: Language, destLang: Language): LiveData<List<Phrase>> {
        return repository.getPhrases(sourceLang, destLang)
    }

    fun add(phrase: Phrase) {
        repository.insert(phrase)
    }

    fun delete(phrase: Phrase) {
        repository.deletePhrase(phrase)
    }

    fun deleteAll() = repository.deleteAll()

    fun deleteSpecificLanguagePair(sourceLang: Language, destLang: Language) = repository.deleteSpecificLanguagePair(sourceLang, destLang)

    override fun onCleared() {
        super.onCleared()
    }
}