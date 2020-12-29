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

    @Query("SELECT * FROM phrases")
    fun getAllPhrases(): LiveData<List<Phrase>>

    @Query("""SELECT * FROM phrases WHERE sourceLang = :sourceLang AND destLang = :destLang""")
    fun getPhrases(sourceLang: Language, destLang: Language): LiveData<List<Phrase>>

    @Query("SELECT * FROM phrases WHERE sourcePhrase = :sourcePhrase")
    fun getDestPhraseFromSource(sourcePhrase: String): LiveData<List<Phrase>>
}