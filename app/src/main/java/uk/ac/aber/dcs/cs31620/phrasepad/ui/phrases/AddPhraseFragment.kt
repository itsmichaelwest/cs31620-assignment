package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.view.*
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentAddPhraseBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Locales
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import java.util.*

class AddPhraseFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddPhraseBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhraseBinding.inflate(inflater, container, false)

        val sourceLang = sharedPreferences.getString("source_lang", "en")?.let { Locales.get(it) }
        val destLang = sharedPreferences.getString("dest_lang", "cy")?.let { Locales.get(it) }

        if (!sharedPreferences.getBoolean("always_english", false)) {
            binding.editTextOriginLang.hint = sourceLang?.localeName
            binding.editTextDestLang.hint = destLang?.localeName
        } else {
            binding.editTextOriginLang.hint = sourceLang?.localeNameEnglish
            binding.editTextDestLang.hint = destLang?.localeNameEnglish
        }

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
            sharedPreferences.getString("source_lang", "en"),
            sharedPreferences.getString("dest_lang", "cy"),
            origin,
            destination
        )

        repository.insert(phrase)

        return true
    }
}