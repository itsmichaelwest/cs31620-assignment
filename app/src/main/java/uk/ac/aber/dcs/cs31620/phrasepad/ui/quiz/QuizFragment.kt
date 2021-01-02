package uk.ac.aber.dcs.cs31620.phrasepad.ui.quiz

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    ): View {
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
                if (selected.destPhrase == correct.destPhrase) {
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
        }

        binding.buttonEndQuiz.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(resources.getString(R.string.quiz_exit_early))
                .setMessage(resources.getString(R.string.quiz_exit_early_message))
                .setPositiveButton(resources.getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
                .setNegativeButton(resources.getString(R.string.yes)) { dialog, _ ->
                    dialog.dismiss()
                    finishQuiz()
                }
                .create()
                .show()
        }

        return binding.root
    }

    private fun refreshQuizQuestions() {
        val fourPhraseList = phraseViewModel.getFourPhrases(
            sourceLang.getCode(),
            destLang.getCode()
        )

        fourPhraseList.observe(viewLifecycleOwner) { phrases ->
            if (phrases.isNotEmpty()) {
                quizRecyclerAdapter.changeData(phrases.toMutableList())

                correct = phrases.shuffled().take(1)[0]
                binding.phraseTested.text = correct.destPhrase
                binding.buttonStartQuiz.visibility = View.GONE
                binding.quizLayout.visibility = View.VISIBLE
            } else {
                AlertDialog.Builder(context)
                    .setTitle(resources.getString(R.string.start_quiz_error_title))
                    .setMessage(resources.getString(R.string.start_quiz_error_message))
                    .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
            }
        }
    }

    private fun finishQuiz() {

    }
}