package uk.ac.aber.dcs.cs31620.phrasepad.ui.quiz

/**
 * Interface to handle onClick events in [QuizRecyclerAdapter].
 * @since 1.0
 */
interface QuizItemClickListener {
    /**
     * Retrieve the position of the item.
     * @return The position of the item.
     * @since 1.0
     */
    fun onItemClick(position: Int)
}