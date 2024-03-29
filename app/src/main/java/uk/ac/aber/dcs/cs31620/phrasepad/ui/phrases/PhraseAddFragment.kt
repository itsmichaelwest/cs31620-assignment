package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentAddPhraseBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.util.*

/**
 * A [BottomSheetDialogFragment] that allows users to add new [Phrase] objects to the database.
 *
 * @since 1.0
 */
class PhraseAddFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddPhraseBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val phraseViewModel: PhraseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPhraseBinding.inflate(inflater, container, false)

        // Get source/destination languages from preferences
        val sourceLanguage = Language(Locale(sharedPreferences.getString("source_lang", "eng")!!))
        val destinationLanguage =
            Language(Locale(sharedPreferences.getString("dest_lang", "eng")!!))

        // Use device locale specific names if requested
        binding.editTextOriginLang.hint = sourceLanguage.getPreferredName(requireContext())
        binding.editTextDestLang.hint = destinationLanguage.getPreferredName(requireContext())

        // Set language flags
        binding.addPhraseSheet.findViewById<ImageView>(R.id.sourceLangFlag)
            .setImageDrawable(sourceLanguage.getFlag(requireContext()))
        binding.addPhraseSheet.findViewById<ImageView>(R.id.destLangFlag)
            .setImageDrawable(destinationLanguage.getFlag(requireContext()))

        binding.saveButton.setOnClickListener {
            if (binding.textInputOriginLang.text.isNullOrEmpty()) {
                binding.editTextOriginLang.error = resources.getString(R.string.phrase_entry_error)
            }

            if (binding.textInputDestLang.text.isNullOrEmpty()) {
                binding.editTextDestLang.error = resources.getString(R.string.phrase_entry_error)
            }

            if (savePhrase(
                    sourceLanguage,
                    destinationLanguage,
                    binding.textInputOriginLang.text.toString(),
                    binding.textInputDestLang.text.toString()
                )
            ) {
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

    /**
     * Save the inserted [Phrase] to the database using [PhraseViewModel].
     * @param sourceLang The source language of the phrase as a [Language] object.
     * @param destLang The destination language of the phrase as a [Language] object.
     * @param origin The source phrase.
     * @param destination The destination phrase.
     * @return True if the phrase was added successfully, false if not (generally because the text
     * fields are empty)
     */
    private fun savePhrase(
        sourceLang: Language,
        destinationLang: Language,
        sourcePhrase: String,
        destinationPhrase: String
    ): Boolean {
        return if (sourcePhrase.isEmpty() || destinationPhrase.isEmpty()) {
            false
        } else {
            val phrase = Phrase(
                0,
                sourceLang.getCode(),
                destinationLang.getCode(),
                sourcePhrase,
                destinationPhrase
            )

            phraseViewModel.add(phrase)

            true
        }
    }
}