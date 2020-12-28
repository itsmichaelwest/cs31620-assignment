package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.phrase_list_item.view.*
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentPhrasesBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [PhrasesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhrasesFragment : Fragment() {

    private lateinit var fragmentPhrasesBinding: FragmentPhrasesBinding
    private lateinit var phraseRecyclerAdapter: PhrasesRecyclerAdapter
    private val phraseViewModel: PhraseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPhrasesBinding = FragmentPhrasesBinding.inflate(inflater, container, false)

        addPhraseRecyclerView()

        return fragmentPhrasesBinding.root
    }

    private fun addPhraseRecyclerView() {
        val phraseRecyclerView = fragmentPhrasesBinding.phraseRecyclerview

        phraseRecyclerAdapter = PhrasesRecyclerAdapter(context)
        phraseRecyclerView.adapter = phraseRecyclerAdapter

        val source = Language("English", Locale.ENGLISH)
        val destin = Language("Welsh", Locale("cy_gb"))

        val phraseList = phraseViewModel.getPhrases(source, destin)

        if (phraseList != null) {
            if (!phraseList.hasObservers()) {
                phraseList.observe(viewLifecycleOwner) { phrases ->
                    phraseRecyclerAdapter.changeData(phrases.toMutableList())
                }
            }
        }

        phraseRecyclerAdapter.clickListener = View.OnClickListener { view ->
            val sourceView: TextView = view.findViewById(R.id.source_text)
            Toast.makeText(context, "Phrase ${sourceView.text} clicked", Toast.LENGTH_SHORT).show()
        }
    }
}