package uk.ac.aber.dcs.cs31620.phrasepad.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository

class PhraseViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PhrasepadRepository = PhrasepadRepository(application)
    //private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    fun getPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>> {
        //Log.d("Preferences", sourceLang.locale.toLanguageTag())
        //Log.d("Preferences", destLang.locale.toLanguageTag())
        Log.d("getPhrases", repository.getPhrases(sourceLang, destLang).toString())
        Log.d("getPhrases", sourceLang)
        Log.d("getPhrases", destLang)
        return repository.getPhrases(sourceLang, destLang)
    }

    override fun onCleared() {
        super.onCleared()
    }
}