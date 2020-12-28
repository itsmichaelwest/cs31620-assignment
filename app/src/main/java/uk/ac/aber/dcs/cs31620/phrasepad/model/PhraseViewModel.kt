package uk.ac.aber.dcs.cs31620.phrasepad.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository

class PhraseViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PhrasepadRepository = PhrasepadRepository(application)
    var phraseList: LiveData<List<Phrase>> = repository.getAllPhrases()
        private set


    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    val sourceLang = sharedPreferences.getString("source_lang", "")
    val destLang = sharedPreferences.getString("dest_lang", "")

    fun getPhrases(knownLanguage: Language, unknownLanguage: Language): LiveData<List<Phrase>> {
        var changed = false

        if (sourceLang != null) {
            Log.d("Preferences", sourceLang)
        }
        if (destLang != null) {
            Log.d("Preferences", destLang)
        }

        phraseList = repository.getAllPhrases()

        return phraseList
    }

    override fun onCleared() {
        super.onCleared()
    }
}