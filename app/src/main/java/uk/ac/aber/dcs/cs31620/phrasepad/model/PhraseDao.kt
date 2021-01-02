package uk.ac.aber.dcs.cs31620.phrasepad.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhraseDao {
    @Insert
    fun insertSinglePhrase(phrase: Phrase)

    @Insert
    fun insertMultiplePhrases(phraseList: List<Phrase>)

    @Update
    fun updatePhrase(phrase: Phrase)

    @Delete
    fun deletePhrase(phrase: Phrase)

    @Query("DELETE FROM phrases")
    fun deleteAll()

    @Query("DELETE FROM phrases WHERE sourceLang = :sourceLang AND destLang = :destLang")
    fun deleteSpecificLanguagePair(sourceLang: String, destLang: String)

    @Query("SELECT * FROM phrases")
    fun getAllPhrases(): LiveData<List<Phrase>>

    @Query("SELECT * FROM phrases WHERE sourceLang = :sourceLang AND destLang = :destLang")
    fun getPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>>

    @Query("SELECT * FROM phrases WHERE sourcePhrase = :sourcePhrase")
    fun getDestPhraseFromSource(sourcePhrase: String): LiveData<Phrase>

    @Query(
        """
        SELECT * FROM phrases WHERE sourceLang = :sourceLang AND destLang = :destLang
        ORDER BY RANDOM()
        LIMIT 4
    """
    )
    fun getFourPhrases(sourceLang: String, destLang: String): LiveData<List<Phrase>>
}