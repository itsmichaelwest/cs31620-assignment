package uk.ac.aber.dcs.cs31620.phrasepad.ui.quiz

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentQuizBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [Quiz.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var quizRecyclerAdapter: QuizRecyclerAdapter
    private val phraseViewModel: PhraseViewModel by viewModels()

    private lateinit var sourceLang: Language
    private lateinit var destLang: Language

    private lateinit var correct: Phrase

    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sourceLang = Language(Locale(sharedPreferences.getString("source_lang", "eng")!!))
        destLang = Language(Locale(sharedPreferences.getString("dest_lang", "eng")!!))

        quizRecyclerAdapter = QuizRecyclerAdapter(context)
        binding.quizQuestionList.adapter = quizRecyclerAdapter
        binding.quizQuestionList.layoutManager = LinearLayoutManager(activity)
        binding.score.text = resources.getString(R.string.quiz_your_score, score)

        quizRecyclerAdapter.setListener(object : QuizRecyclerAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                val selected = quizRecyclerAdapter.getPhraseAt(position)
                if (selected == correct) {
                    Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
                    score++
                    binding.score.text = resources.getString(R.string.quiz_your_score, score)
                    refreshQuizQuestions()
                } else {
                    Toast.makeText(context, "Incorrect, try again.", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.buttonStartQuiz.setOnClickListener {
            refreshQuizQuestions()
            binding.buttonStartQuiz.visibility = View.GONE
            binding.quizLayout.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun refreshQuizQuestions() {
        val fourPhraseList = phraseViewModel.getFourPhrases(
            sourceLang.getCode(),
            destLang.getCode()
        )

        fourPhraseList.observe(viewLifecycleOwner) { phrases ->
            quizRecyclerAdapter.changeData(phrases.toMutableList())

            correct = phrases.shuffled().take(1)[0]
            binding.phraseTested.text = correct.destPhrase
        }
    }
}