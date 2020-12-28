package uk.ac.aber.dcs.cs31620.phrasepad.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository

class PhraseViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PhrasepadRepository = PhrasepadRepository(application)
    var phraseList: LiveData<List<Phrase>> = repository.getAllPhrases()

    //private val languages = application.resources.getStringArray(R.id.language_settings)
    //private val sourceLanguage= languages[0]
    //private val destinationLanguage = languages[1]

    fun getPhrases(knownLanguage: Language, unknownLanguage: Language): LiveData<List<Phrase>> {
        var changed = false

        phraseList = repository.getAllPhrases()

        return phraseList
    }

    override fun onCleared() {
        super.onCleared()
    }
}