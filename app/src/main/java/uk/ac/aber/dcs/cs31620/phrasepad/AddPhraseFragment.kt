package uk.ac.aber.dcs.cs31620.phrasepad

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_add_phrase.*
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadDatabase
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentAddPhraseBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import java.util.*

class AddPhraseFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddPhraseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhraseBinding.inflate(inflater, container, false)

        binding.saveButton.setOnClickListener { view ->
            Log.d("saveButton", "Selected")
            if (savePhrase(binding.textInputOriginLang.text.toString(), binding.textInputDestLang.text.toString())) {
                dismiss()
                getView()?.rootView?.layout?.let { Toast.makeText(it.context, "Successfuly added phrase!", Toast.LENGTH_LONG).show() }
            }
        }

        return binding.root
    }

    fun savePhrase(origin: String, destination: String): Boolean {
        val repository = PhrasepadRepository(requireActivity().application)

        val phrase = Phrase(
            0,
            Language("English", Locale.ENGLISH),
            Language("Welsh", Locale("cy_gb")),
            origin,
            destination
        )

        repository.insert(phrase)

        return true
    }
}