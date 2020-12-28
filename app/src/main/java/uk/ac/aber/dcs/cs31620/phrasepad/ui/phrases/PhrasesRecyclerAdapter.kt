package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.PhraseListItemBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase

class PhrasesRecyclerAdapter(private val context: Context?) : RecyclerView.Adapter<PhrasesRecyclerAdapter.ViewHolder>() {
    private var data: MutableList<Phrase> = mutableListOf()
    var clickListener: View.OnClickListener? = null

    inner class ViewHolder(
        itemView: View,
        private val source: TextView,
        private val translated: TextView
    ): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(clickListener)
        }

        fun bindData(phrase: Phrase) {
            Log.d("PhrasesRecyclerBindData", "Binding data (inner)!")
            source.text = phrase.sourcePhrase
            translated.text = phrase.destPhrase
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhrasesRecyclerAdapter.ViewHolder {
        val binding = PhraseListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(
            binding.cell,
            binding.sourceText,
            binding.translatedText
        )
    }

    override fun onBindViewHolder(holder: PhrasesRecyclerAdapter.ViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    fun changeData(data: MutableList<Phrase>) {

        this.data = data
        this.notifyDataSetChanged()
    }
}