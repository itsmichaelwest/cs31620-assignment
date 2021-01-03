package uk.ac.aber.dcs.cs31620.phrasepad.ui.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.QuizListItemBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase

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

    fun changeData(data: List<Phrase>) {
        this.data = data
        this.notifyDataSetChanged()
    }

    fun getPhraseAt(id: Int): Phrase = data[id]

    //set method
    fun setListener(listener: QuizItemClickListener?) {
        this.listener = listener
    }
}