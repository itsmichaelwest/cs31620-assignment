package uk.ac.aber.dcs.cs31620.phrasepad.ui.quiz

import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase

class QuizHelper {
    fun pickFourPhrases(phrases: MutableList<Phrase>) : MutableList<Phrase> {
        // Shuffle the data and take 4 items
        phrases.shuffle()
        return phrases.take(4).toMutableList()
    }
}