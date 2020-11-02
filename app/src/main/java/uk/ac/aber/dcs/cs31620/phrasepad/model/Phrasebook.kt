package uk.ac.aber.dcs.cs31620.phrasepad.model

class Phrasebook(l1: Language, l2: Language) {
    val knownLanguage = l1
    val unknownLanguage = l2
    var phrasebook = mutableMapOf("knownPhrase" to "known", "unknownPhrase" to "unknown")

    fun addPhrase(known: String, unknown: String) {
        phrasebook[known] = unknown
    }

    fun getPhrase(search: String): String {
        return phrasebook[search].toString()
    }
}