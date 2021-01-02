package uk.ac.aber.dcs.cs31620.phrasepad.model

import androidx.room.*

@Entity(tableName = "phrases")
data class Phrase(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var sourceLang: String,
    var destLang: String,
    var sourcePhrase: String,
    var destPhrase: String
)