package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentPhraseDetailBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.LocaleHelper
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.util.*

/**
 * A [BottomSheetDialogFragment] that shows more detailed information about a [Phrase] and allows
 * them to delete it.
 */
class PhraseDetailFragment(private var phrase: Phrase) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPhraseDetailBinding
    private val phraseViewModel: PhraseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhraseDetailBinding.inflate(inflater, container, false)

        val source = Language(Locale(phrase.sourceLang))
        val destination = Language(Locale(phrase.destLang))

        binding.sourceLangFlag.setImageDrawable(source.getFlag(requireContext()))
        binding.sourceLangName.text = source.getPreferredName(requireContext())
        binding.sourceLangText.text = phrase.sourcePhrase

        binding.destLangFlag.setImageDrawable(destination.getFlag(requireContext()))
        binding.destLangName.text = destination.getPreferredName(requireContext())
        binding.destLangText.text = phrase.destPhrase

        binding.deletePhrase.setOnClickListener {
            phraseViewModel.delete(phrase)
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val bottomSheet = dialog!!.findViewById<ConstraintLayout>(R.id.phraseDetailSheet)
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
}