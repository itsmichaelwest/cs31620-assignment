package uk.ac.aber.dcs.cs31620.phrasepad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentPhrasesBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PhrasesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhrasesFragment : Fragment() {

    private lateinit var fragmentPhrasesBinding: FragmentPhrasesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPhrasesBinding = FragmentPhrasesBinding.inflate(inflater, container, false)

        viewManager = LinearLayoutManager(activity)

        recyclerView = fragmentPhrasesBinding.phraseRecyclerview.apply {
            layoutManager = viewManager
            // adapter = viewAdapter
        }

        val repository = PhrasepadRepository(requireActivity().application)

        return fragmentPhrasesBinding.root
    }
}