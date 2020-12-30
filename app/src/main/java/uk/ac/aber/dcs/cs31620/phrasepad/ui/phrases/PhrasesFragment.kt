package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_phrases.*
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentPhrasesBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.util.*

class PhrasesFragment : Fragment() {

    private var oldPhraseList: LiveData<List<Phrase>>? = null

    private lateinit var phraseRecyclerAdapter: PhrasesRecyclerAdapter
    private lateinit var binding: FragmentPhrasesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val phraseViewModel: PhraseViewModel by viewModels()
    private lateinit var phrasesList: RecyclerView

    private lateinit var sourceLang: Language
    private lateinit var destLang: Language

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhrasesBinding.inflate(inflater, container, false)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sourceLang = Language(Locale(sharedPreferences.getString("source_lang", "en")))
        destLang = Language(Locale(sharedPreferences.getString("dest_lang", "cy")))

        binding.phrasesListSwipeRefresh.setOnRefreshListener {
            sourceLang = Language(Locale(sharedPreferences.getString("source_lang", "en")))
            destLang = Language(Locale(sharedPreferences.getString("dest_lang", "cy")))

            refreshRecyclerViewData(sourceLang, destLang)

            phrasesListSwipeRefresh.isRefreshing = false
        }

        phrasesList = binding.phrasesList

        phraseRecyclerAdapter = PhrasesRecyclerAdapter(context)
        phrasesList.adapter = phraseRecyclerAdapter
        phrasesList.layoutManager = LinearLayoutManager(activity)

        addPhraseRecyclerView()
        refreshRecyclerViewData(sourceLang, destLang)

        return binding.root
    }

    private fun addPhraseRecyclerView() {
        phraseRecyclerAdapter.clickListener = View.OnClickListener { view ->
            val sourceView: TextView = view.findViewById(R.id.source_text)
            Toast.makeText(context, "Phrase ${sourceView.text} clicked", Toast.LENGTH_SHORT).show()
        }

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.layoutPosition
                        val phrase: Phrase = phraseRecyclerAdapter.getPhraseAt(position)
                        phraseViewModel.delete(phrase)

                        Snackbar.make(
                            activity!!.findViewById(android.R.id.content),
                            "Deleted",
                            Snackbar.LENGTH_LONG
                        ).apply {
                            setAction("Undo") {
                                phraseViewModel.add(phrase)
                                phrasesList.scrollToPosition(position)
                            }
                        }
                    }
                }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(phrasesList)
    }

    private fun refreshRecyclerViewData(sourceLang: Language, destLang: Language) {
        val phraseList = phraseViewModel.getPhrases(sourceLang.getCode(), destLang.getCode())

        if (oldPhraseList != phraseList) {
            oldPhraseList?.removeObservers(viewLifecycleOwner)
            oldPhraseList = phraseList
        }

        if (!phraseList.hasObservers()) {
            phraseList.observe(viewLifecycleOwner) { phrases ->
                phraseRecyclerAdapter.changeData(phrases.toMutableList())
            }
        }
    }
}