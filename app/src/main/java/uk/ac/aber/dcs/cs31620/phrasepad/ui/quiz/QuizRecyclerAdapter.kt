package uk.ac.aber.dcs.cs31620.phrasepad.ui.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.QuizListItemBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase

/**
 * Adapter to provide a binding from an app-specific data set to views that are displayed within a [RecyclerView].
 *
 * @param context [Context]
 * @since 1.0
 * @see [RecyclerView.Adapter]
 */
class QuizRecyclerAdapter(private val context: Context?) :
    RecyclerView.Adapter<QuizRecyclerAdapter.ViewHolder>() {
    private var data: List<Phrase> = listOf()
    private var listener: QuizItemClickListener? = null

    inner class ViewHolder(itemView: View, private val phraseToTest: TextView) :
        RecyclerView.ViewHolder(itemView) {
        fun bindData(phrase: Phrase) {
            phraseToTest.text = phrase.sourcePhrase
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuizListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(
            binding.cell,
            binding.phraseToTest
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = data.size

    /**
     * Changes the data used by the [RecyclerView]. Will call [notifyDataSetChanged] to tell the view
     * that the data has been updated.
     * @param data A [List] of [Phrase] objects to replace the existing data in the view.
     * @since 1.0
     */
    fun changeData(data: List<Phrase>) {
        this.data = data
        this.notifyDataSetChanged()
    }

    /**
     * Retrieves the [Phrase] at the selected index.
     * @param id The selected index in the [RecyclerView].
     * @return The [Phrase] bound to that index.
     * @since 1.0
     */
    fun getPhraseAt(id: Int): Phrase = data[id]

    /**
     * Set the [QuizItemClickListener] for the [RecyclerView].
     * @param listener The [QuizItemClickListener] to set.
     * @since 1.0
     */
    fun setListener(listener: QuizItemClickListener?) {
        this.listener = listener
    }
}