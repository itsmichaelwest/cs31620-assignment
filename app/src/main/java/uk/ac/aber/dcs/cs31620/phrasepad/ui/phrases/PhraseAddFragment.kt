package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_phrase.*
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.data.PhrasepadRepository
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentAddPhraseBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import java.util.*

class PhraseAddFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddPhraseBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhraseBinding.inflate(inflater, container, false)

        // Get source/destination languages from preferences
        val sourceLanguage = Language(Locale(sharedPreferences.getString("source_lang", "en")!!))
        val destinationLanguage = Language(Locale(sharedPreferences.getString("dest_lang", "en")!!))

        // Use device locale specific names if requested
        val alwaysDevLang = sharedPreferences.getBoolean("always_dev_lang", false)
        if (alwaysDevLang) {
            binding.editTextOriginLang.hint = sourceLanguage.getDeviceLangName()
            binding.editTextDestLang.hint = destinationLanguage.getDeviceLangName()
        } else {
            binding.editTextOriginLang.hint = sourceLanguage.getNativeName()
            binding.editTextDestLang.hint = destinationLanguage.getNativeName()
        }

        // Set language flags
        binding.addPhraseSheet.findViewById<ImageView>(R.id.sourceLangFlag).setImageDrawable(sourceLanguage.getFlag(requireContext()))
        binding.addPhraseSheet.findViewById<ImageView>(R.id.destLangFlag).setImageDrawable(destinationLanguage.getFlag(requireContext()))

        binding.saveButton.setOnClickListener {
            if (textInputOriginLang.text.isNullOrEmpty()) {
                editTextOriginLang.error = resources.getString(R.string.phrase_entry_error)
            }

            if (textInputDestLang.text.isNullOrEmpty()) {
                editTextDestLang.error = resources.getString(R.string.phrase_entry_error)
            }

            if (savePhrase(binding.textInputOriginLang.text.toString(), binding.textInputDestLang.text.toString(), sourceLanguage, destinationLanguage)) {
                dismiss()
            }
        }

        binding.editTextOriginLang.requestFocus()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val bottomSheet = dialog!!.findViewById<ConstraintLayout>(R.id.add_phrase_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        view?.post {
            val parent = requireView().parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior

            val bottomSheetBehavior = behavior as BottomSheetBehavior
            bottomSheetBehavior.setPeekHeight(requireView().measuredHeight)
        }
    }

    private fun savePhrase(origin: String, destination: String, sourceLang: Language, destLang: Language): Boolean {
        if (origin.isEmpty() || destination.isEmpty()) {
            return false
        } else {
            val repository = PhrasepadRepository(requireActivity().application)

            val phrase = Phrase(
                0,
                sourceLang.getCode(),
                destLang.getCode(),
                origin,
                destination
            )

            repository.insert(phrase)

            return true
        }
    }
}