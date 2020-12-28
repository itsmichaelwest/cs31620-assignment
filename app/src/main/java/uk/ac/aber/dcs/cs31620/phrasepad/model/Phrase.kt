package uk.ac.aber.dcs.cs31620.phrasepad.model

import androidx.room.*

@Entity(tableName = "phrases")
data class Phrase (
    @PrimaryKey(autoGenerate = true) val id: Int,
    var knownLanguage: Language,
    var unknownLanguage: Language,
    var knownPhrase: String,
    var unknownPhrase: String
)