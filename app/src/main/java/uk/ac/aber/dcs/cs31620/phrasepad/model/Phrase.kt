package uk.ac.aber.dcs.cs31620.phrasepad.model

import androidx.room.*

/**
 * Represents a phrase entity in a Room database.
 *
 * @param id Primary key, automatically generated. Usually, you pass 0 to this.
 * @param sourceLang The source language of the phrase as a string. This should usually be an ISO 639-3 code.
 * @param destLang The destination language of the phrase as a string. This should usually be an ISO 639-3 code.
 * @param sourcePhrase The source phrase.
 * @param destPhrase The destination phrase.
 * @see [PhraseDao]
 */
@Entity(tableName = "phrases")
data class Phrase(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var sourceLang: String,
    var destLang: String,
    var sourcePhrase: String,
    var destPhrase: String
)