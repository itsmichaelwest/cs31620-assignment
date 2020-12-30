package uk.ac.aber.dcs.cs31620.phrasepad.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.core.view.isEmpty
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_set_languages.*
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentSetLanguagesBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import java.util.*


class SetLanguagesFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSetLanguagesBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetLanguagesBinding.inflate(inflater, container, false)

        val languageCodes = resources.getStringArray(R.array.languages_codes).toList()
        val languageList: MutableList<Language> = mutableListOf()
        for (language in languageCodes) {
            languageList.add(Language(Locale(language)))
        }

        val adapter = SetLanguagesAutocompleteAdapter(requireContext(), R.layout.language_list_item, languageList)

        (binding.sourceLangDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.destLangDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        var sourceLang: Language? = null
        var destLang: Language? = null

        (binding.sourceLangDropdown.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
            sourceLang = adapter.getItem(position)
        }

        (binding.destLangDropdown.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
            destLang = adapter.getItem(position)
        }

        binding.saveButton.setOnClickListener {
            var isReadyToCommitSource = false
            var isReadyToCommitDest = false

            if (sourceLang == null)
                binding.sourceLangDropdown.error = resources.getString(R.string.set_language_error)
            else
                isReadyToCommitSource = true

            if (destLang == null)
                binding.destLangDropdown.error = resources.getString(R.string.set_language_error)
            else
                isReadyToCommitDest = true

            if(isReadyToCommitSource && isReadyToCommitDest) {
                val editor = sharedPreferences.edit()
                editor.putString("source_lang", sourceLang?.getCode())
                editor.putString("dest_lang", destLang?.getCode())
                editor.apply()
                dismiss()
            }
        }

        return binding.root
    }
}