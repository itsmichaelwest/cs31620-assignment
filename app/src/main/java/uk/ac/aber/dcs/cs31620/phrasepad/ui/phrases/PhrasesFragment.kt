package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.phrase_list_item.view.*
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentPhrasesBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Locales
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.util.*

class PhrasesFragment : Fragment() {

    private var oldPhraseList: LiveData<List<Phrase>>? = null

    private lateinit var phraseRecyclerAdapter: PhrasesRecyclerAdapter
    private lateinit var phrasesFragmentBinding: FragmentPhrasesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val phraseViewModel: PhraseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        phrasesFragmentBinding = FragmentPhrasesBinding.inflate(inflater, container, false)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        addPhraseRecyclerView()

        return phrasesFragmentBinding.root
    }

    private fun addPhraseRecyclerView() {
        val phrasesList = phrasesFragmentBinding.phrasesList

        phraseRecyclerAdapter = PhrasesRecyclerAdapter(context)
        phrasesList.adapter = phraseRecyclerAdapter
        phrasesList.layoutManager = LinearLayoutManager(activity)

        val sourceLang = sharedPreferences.getString("source_lang", "en")?.let { Locales.get(it) }
        val destLang = sharedPreferences.getString("dest_lang", "cy")?.let { Locales.get(it) }

        phraseRecyclerAdapter.clickListener = View.OnClickListener { view ->
            val sourceView: TextView = view.findViewById(R.id.source_text)
            Toast.makeText(context, "Phrase ${sourceView.text} clicked", Toast.LENGTH_SHORT).show()
        }

        val phraseList = phraseViewModel.getPhrases(sourceLang!!.localeCode, destLang!!.localeCode)

        if (oldPhraseList != phraseList) {
            oldPhraseList?.removeObservers(viewLifecycleOwner)
            oldPhraseList = phraseList
        }

        if (!phraseList.hasObservers()) {
            phraseList.observe(viewLifecycleOwner) { phrases ->
                Log.d("PhrasesFragment", "Observation!")
                phraseRecyclerAdapter.changeData(phrases.toMutableList())
            }
        }
    }
}