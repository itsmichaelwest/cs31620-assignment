package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.PhraseListItemBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase

/**
 * Adapter to provide a binding from an app-specific data set to views that are displayed within a [RecyclerView].
 *
 * @param context [Context]
 * @see [RecyclerView.Adapter]
 */
class PhrasesRecyclerAdapter(private val context: Context?) :
    RecyclerView.Adapter<PhrasesRecyclerAdapter.ViewHolder>() {

    private var data: MutableList<Phrase> = mutableListOf()
    private var clickListener: View.OnClickListener? = null

    inner class ViewHolder(
        itemView: View,
        private val source: TextView,
        private val translated: TextView
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(clickListener)
        }

        /**
         * Binds the [Phrase] data to the UI elements of the view.
         * @param phrase The [Phrase] object to be bound.
         * @since 1.0
         */
        fun bindData(phrase: Phrase) {
            source.text = phrase.sourcePhrase
            translated.text = phrase.destPhrase
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhrasesRecyclerAdapter.ViewHolder {
        val binding = PhraseListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(
            binding.cell,
            binding.sourceText,
            binding.translatedText
        )
    }

    override fun onBindViewHolder(holder: PhrasesRecyclerAdapter.ViewHolder, position: Int) {
        holder.bindData(data[position])
        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as AppCompatActivity
            val fragment = PhraseDetailFragment(getPhraseAt(position))
            fragment.show(activity.supportFragmentManager, "phrase_detail_fragment")
        }
    }

    /**
     * Changes the data used by the [RecyclerView]. Will call [notifyDataSetChanged] to tell the view
     * that the data has been updated.
     * @param data A [MutableList] of [Phrase] objects to replace the existing data in the view.
     * @since 1.0
     */
    fun changeData(data: MutableList<Phrase>) {
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
}