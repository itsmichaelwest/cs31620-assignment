package uk.ac.aber.dcs.cs31620.phrasepad.ui.settings

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentSetLanguagesBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.LocaleHelper
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases.PhrasesRecyclerAdapter
import java.util.*


/**
 * A [BottomSheetDialogFragment] that deals with setting up the app on first run.
 *
 * @since 1.0
 */
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
    ): View {
        binding = FragmentSetLanguagesBinding.inflate(inflater, container, false)

        val languageList = LocaleHelper.getAll()
        val languageStringList: MutableList<Language> = mutableListOf()

        for (lang in languageList) {
            languageStringList.add(Language(Locale(lang.iso3Language)))
        }

        val adapter = SetLanguagesAutocompleteAdapter(
            requireContext(),
            R.layout.language_list_item,
            languageStringList
        )

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

            if (isReadyToCommitSource && isReadyToCommitDest) {
                val editor = sharedPreferences.edit()
                editor.putString("source_lang", sourceLang?.getCode())
                editor.putString("dest_lang", destLang?.getCode())
                editor.apply()
                dismiss()
            }
        }

        binding.nativeToggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("always_dev_lang", isChecked)
                apply()
            }
            adapter.notifyDataSetChanged()
        }

        // Start with a sample database, English to Welsh with 20 phrases
        binding.startWithSampleButton.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(resources.getString(R.string.set_sample_dialog_title))
                .setMessage(resources.getString(R.string.set_sample_dialog_message))
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    // Set source language to English, destination to Welsh
                    val editor = sharedPreferences.edit()
                    editor.putString("source_lang", "eng")
                    editor.putString("dest_lang", "cym")
                    editor.putBoolean("sample_database", true)
                    editor.apply()
                    // Dismiss sheet and go to app
                    this.dismiss()
                }
                .setNegativeButton(resources.getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }

        return binding.root
    }
}