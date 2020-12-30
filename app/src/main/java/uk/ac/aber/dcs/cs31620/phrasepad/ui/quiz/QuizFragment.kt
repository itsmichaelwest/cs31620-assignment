package uk.ac.aber.dcs.cs31620.phrasepad.ui.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentQuizBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Quiz.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    lateinit var binding: FragmentQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater)

        /*
        binding.buttonNewFrag.setOnClickListener {
            val fragment = QuizFourChoicesFragment()
            Log.d("QuizFragment", "Button pressed")
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.quizFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
         */

        return binding.root
    }
}