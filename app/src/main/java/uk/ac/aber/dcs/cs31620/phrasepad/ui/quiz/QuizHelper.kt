package uk.ac.aber.dcs.cs31620.phrasepad.ui.quiz

import android.util.Log
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase

class QuizHelper() {
    fun pickFourPhrases(phrases: MutableList<Phrase>) : List<Phrase> {
        val dataSubset: MutableList<Phrase> = mutableListOf()

        for (d in phrases) {
            Log.d("QuizHelperData", "${d.sourcePhrase} + ${d.destPhrase}")
        }

        // Shuffle the data and take 4 items
        phrases.shuffle()
        val dataTaken = phrases.take(4)

        for (data in dataTaken) {
            Log.d("QuizHelper", "${data.sourcePhrase} + ${data.destPhrase}")
        }

        return dataSubset
    }
}