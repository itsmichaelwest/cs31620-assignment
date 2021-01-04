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
import androidx.recyclerview.widget.RecyclerView
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentQuizBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases.PhrasesRecyclerAdapter
import java.util.*

/**
 * A [Fragment] to deal with displaying a select number phrases in a [RecyclerView] using [QuizRecyclerAdapter].
 * Users then select a specific phrase and the logic will check to see if that phrase is correct.
 *
 * @since 1.0
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
    private var totalTaken = 0

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

        quizRecyclerAdapter.setListener(object : QuizItemClickListener {
            override fun onItemClick(position: Int) {
                val selected = quizRecyclerAdapter.getPhraseAt(position)
                if (selected.destPhrase == correct.destPhrase) {
                    score++
                    totalTaken++
                    binding.score.text = resources.getString(R.string.quiz_your_score, score)
                    refreshQuizQuestions()
                } else {
                    totalTaken++
                    Toast.makeText(context, resources.getString(R.string.quiz_incorrect), Toast.LENGTH_SHORT).show()
                    refreshQuizQuestions()
                }
            }
        })

        binding.buttonStartQuiz.setOnClickListener {
            refreshQuizQuestions()
        }

        binding.buttonEndQuiz.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(resources.getString(R.string.quiz_exit_early))
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

    /**
     * Refresh the quiz questions by retrieving a new set of four phrases from the database.
     * @since 1.0
     */
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

    /**
     * Display an [AlertDialog] showing the user their score and then hide the quiz layout from the view.
     * @since 1.0
     */
    private fun finishQuiz() {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.quiz_complete_title))
            .setMessage(resources.getString(R.string.quiz_complete_message, score, totalTaken))
            .setPositiveButton(resources.getString(R.string.finish)) { dialog, _ ->
                dialog.dismiss()
                binding.buttonStartQuiz.visibility = View.VISIBLE
                binding.quizLayout.visibility = View.GONE
            }
            .setCancelable(false)
            .create()
            .show()
    }
}