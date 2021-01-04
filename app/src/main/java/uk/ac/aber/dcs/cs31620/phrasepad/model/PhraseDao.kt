package uk.ac.aber.dcs.cs31620.phrasepad.model

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Interface for database interactions, including insert, update, delete, and query methods.
 * @since 1.0
 */
@Dao
interface PhraseDao {
    /**
     * Inserts a single phrase into the database.
     * @param phrase The [Phrase] to insert.
     * @since 1.0
     */
    @Insert
    fun insertSinglePhrase(phrase: Phrase)

    /**
     * Inserts multiple phrases into the database.
     * @param phraseList A [List] of [Phrase] objects to insert.
     * @since 1.0
     */
    @Insert
    fun insertMultiplePhrases(phraseList: List<Phrase>)

    /**
     * Updates a phrase in the database
     * @param phrase The [Phrase] to update
     * @since 1.0
     */
    @Update
    fun updatePhrase(phrase: Phrase)

    /**
     * Deletes a phrase from the database
     * @param phrase The [Phrase] to delete.
     * @since 1.0
     */
    @Delete
    fun deletePhrase(phrase: Phrase)

    /**
     * Deletes all phrases in the database. This is a rather destructive action, avoid using where possible.
     * @since 1.0
     * @see [deleteSpecificLanguagePair]
     */
    @Query("DELETE FROM phrases")
    fun deleteAll()

    /**
     * Delete a specific language pair from the database. This is the more appropriate form of deletion.
     * @param sourceLang The source language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @param destLang The destination language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @since 1.0
     */
    @Query("DELETE FROM phrases WHERE sourceLang = :sourceLang AND destLang = :destLang")
    fun deleteSpecificLanguagePair(sourceLang: String, destLang: String)

    /**
     * Retrieve all phrases from the database. This may include multiple different languages, depending
     * on the user's configuration, and so avoid using unless this is the intent.
     * @return A [LiveData] list of [Phrase] objects.
     * @since 1.0
     */
    @Query("SELECT * FROM phrases")
    fun getAllPhrases(): LiveData<List<Phrase>>

    /**
     * Retrieve a specific language pair from the database. This the more appropriate form of retrieval.
     * @param sourceLang The source language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @param destLang The destination language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @return A [LiveData] list of [Phrase] objects.
     * @since 1.0
     */
    @Query("SELECT * FROM phrases WHERE sourceLang = :sourceLang AND destLang = :destLang")
    fun getPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>>

    /**
     * Retrieve a [Phrase] object based on a search for the source string.
     * @param sourcePhrase The source phrase as a string.
     * @return A [LiveData] object containing one [Phrase]
     * @since 1.0
     */
    @Query("SELECT * FROM phrases WHERE sourcePhrase = :sourcePhrase")
    fun getDestPhraseFromSource(sourcePhrase: String): LiveData<Phrase>

    /**
     * Retrieve four randomized [Phrase] objects based on a specific language pair.
     * @param sourceLang The source language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @param destLang The destination language of the phrase as a string. This should usually be an ISO 639-3 code.
     * @return A [LiveData] list of four [Phrase] objects.
     * @since 1.0
     */
    @Query(
        """
        SELECT * FROM phrases WHERE sourceLang = :sourceLang AND destLang = :destLang
        ORDER BY RANDOM()
        LIMIT 4
    """
    )
    fun getFourPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>>

    /**
     * Retrieve a [Phrase] object based on a search for the source string. This function is for testing only, as
     * it doesn't return a [LiveData] object.
     * @param sourcePhrase The source phrase as a string.
     * @return A [Phrase] object.
     * @since 1.0
     */
    @Query("SELECT * FROM phrases WHERE sourcePhrase = :sourcePhrase")
    fun testGetDestPhraseFromSource(sourcePhrase: String): Phrase
}