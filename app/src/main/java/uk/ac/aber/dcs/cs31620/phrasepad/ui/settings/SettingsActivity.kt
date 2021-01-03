package uk.ac.aber.dcs.cs31620.phrasepad.ui.settings

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.SettingsActivityBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.LocaleHelper
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.util.*

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: SettingsActivityBinding
    private val phraseViewModel: PhraseViewModel by viewModels()
    private lateinit var sourceLanguage: Language
    private lateinit var destLanguage: Language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the navigation bar color/background based on the system theme. Requires Android 11.
        @RequiresApi(api = Build.VERSION_CODES.R)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val a = TypedValue()
            theme.resolveAttribute(android.R.attr.windowBackground, a, true)
            if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                window.navigationBarColor = a.data
            }

            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    window.decorView.windowInsetsController?.setSystemBarsAppearance(
                        APPEARANCE_LIGHT_NAVIGATION_BARS,
                        APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                }
            }
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        sourceLanguage = Language(Locale(sharedPreferences.getString("source_lang", "en")!!))
        destLanguage = Language(Locale(sharedPreferences.getString("dest_lang", "en")!!))
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            "theme" -> {
                when (sharedPreferences.getString("theme", "0")) {
                    "0" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    "1" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    "2" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }
            "source_lang", "dest_lang" -> {
                if (!isFinishing) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(R.string.language_changed_header)
                        .setMessage(R.string.language_changed_message)
                        .setNegativeButton(R.string.language_changed_delete) { dialog, _ ->
                            phraseViewModel.deleteSpecificLanguagePair(
                                sourceLanguage.getCode(),
                                destLanguage.getCode()
                            )
                            sourceLanguage = Language(
                                Locale(
                                    sharedPreferences.getString(
                                        "source_lang",
                                        "eng"
                                    )!!
                                )
                            )
                            destLanguage = Language(
                                Locale(
                                    sharedPreferences.getString(
                                        "dest_lang",
                                        "eng"
                                    )!!
                                )
                            )
                            Toast.makeText(
                                this,
                                R.string.language_changed_cleared,
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                            finish()
                        }
                        .setPositiveButton(R.string.save) { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        }
                    builder.create()
                    if (!isFinishing) {
                        builder.show()
                    }
                }
            }
        }
    }

    // Go back to main activity when back button in toolbar is pressed
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            setLanguagePreferencesFields()

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
                when (key) {
                    "always_dev_lang" -> {
                        setLanguagePreferencesFields()
                    }
                }
            }

            // Reset preferences button. This is really messy, the app intentionally crashes when we
            // clear the preferences. There must be a better way, but we need the app to close anyway
            // so the user can select a new language pair.
            val resetPreference = findPreference<Preference>("clear_all")
            resetPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(resources.getString(R.string.clear_all) + "?")
                    .setMessage(resources.getString(R.string.clear_all_dialog_message))
                    .setPositiveButton(resources.getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
                    .setNegativeButton(resources.getString(R.string.yes)) { _, _ ->
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()
                        PreferenceManager.setDefaultValues(context, R.xml.root_preferences, true)
                    }
                    .create()
                    .show()
                true
            }
        }

        private fun setLanguagePreferencesFields() {
            val languageList = LocaleHelper.getAll()
            val languageCodeList: Array<CharSequence?> = arrayOfNulls(languageList.size)
            val languageStringList: Array<CharSequence?> = arrayOfNulls(languageList.size)

            for (i in languageCodeList.indices)
                languageCodeList[i] = languageList[i].iso3Language

            for (i in languageStringList.indices)
                languageStringList[i] = Language(Locale(languageList[i].iso3Language)).getPreferredName(requireContext())

            val sourceLangList = findPreference<ListPreference>("source_lang")
            sourceLangList?.entryValues = languageCodeList
            sourceLangList?.entries = languageStringList

            val destLangList = findPreference<ListPreference>("dest_lang")
            destLangList?.entryValues = languageCodeList
            destLangList?.entries = languageStringList
        }
    }
}