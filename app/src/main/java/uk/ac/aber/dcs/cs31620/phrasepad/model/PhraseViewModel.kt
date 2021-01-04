package uk.ac.aber.dcs.cs31620.phrasepad.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository

/**
 * ViewModel for [Phrase] objects, interacts with [PhrasepadRepository].
 *
 * @param application [Application]
 * @since 1.0
 * @see [androidx.lifecycle.ViewModel]
 */
class PhraseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PhrasepadRepository = PhrasepadRepository(application)

    /**
     * Retrieve a specific language pair from the database. This the more appropriate form of retrieval.
     * @param sourceLang The source language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @param destLang The destination language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @return A [LiveData] list of [Phrase] objects.
     * @since 1.0
     */
    fun getPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>> =
        repository.getPhrases(sourceLang, destLang)

    /**
     * Retrieve a [Phrase] object based on a search for the source string.
     * @param sourcePhrase The source phrase as a string.
     * @return A [LiveData] object containing one [Phrase]
     * @since 1.0
     */
    fun getDestPhraseFromSource(sourcePhrase: String): LiveData<Phrase> =
        repository.getDestPhraseFromSource(sourcePhrase)

    /**
     * Retrieve four randomized [Phrase] objects based on a specific language pair.
     * @param sourceLang The source language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @param destLang The destination language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @return A [LiveData] list of four [Phrase] objects.
     * @since 1.0
     */
    fun getFourPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>> =
        repository.getFourPhrases(sourceLang, destLang)

    /**
     * Inserts a single phrase into the database.
     * @param phrase The [Phrase] to insert.
     * @since 1.0
     */
    fun add(phrase: Phrase) = repository.insert(phrase)

    /**
     * Deletes a phrase from the database
     * @param phrase The [Phrase] to delete.
     * @since 1.0
     */
    fun delete(phrase: Phrase) = repository.deletePhrase(phrase)

    /**
     * Deletes all phrases in the database. This is a rather destructive action, avoid using where possible.
     * @since 1.0
     * @see [deleteSpecificLanguagePair]
     */
    fun deleteAll() = repository.deleteAll()

    /**
     * Delete a specific language pair from the database. This is the more appropriate form of deletion.
     * @param sourceLang The source language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @param destLang The destination language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @since 1.0
     */
    fun deleteSpecificLanguagePair(sourceLang: String, destLang: String) =
        repository.deleteSpecificLanguagePair(sourceLang, destLang)
}