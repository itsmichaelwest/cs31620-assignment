package uk.ac.aber.dcs.cs31620.phrasepad.model

import androidx.room.*

@Entity(tableName = "phrases")
data class Phrase (
    @PrimaryKey val id: Int,
    var knownLanguage: String,
    var unknownLanguage: String,
    var knownPhrase: String,
    var unknownPhrase: String
)