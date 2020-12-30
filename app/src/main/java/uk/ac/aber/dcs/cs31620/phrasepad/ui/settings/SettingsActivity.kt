package uk.ac.aber.dcs.cs31620.phrasepad.ui.settings

import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.WindowInsetsController.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import uk.ac.aber.dcs.cs31620.phrasepad.MainActivity
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.SettingsActivityBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseDao
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases.PhrasesFragment
import uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases.PhrasesRecyclerAdapter
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
            val windowBg = theme.resolveAttribute(android.R.attr.windowBackground, a, true)
            if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                window.navigationBarColor = a.data
            }

            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when(currentNightMode) {
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
        when (key){
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
                            phraseViewModel.deleteSpecificLanguagePair(sourceLanguage.getCode(), destLanguage.getCode())
                            sourceLanguage = Language(Locale(sharedPreferences.getString("source_lang", "en")!!))
                            destLanguage = Language(Locale(sharedPreferences.getString("dest_lang", "en")!!))
                            Toast.makeText(this, R.string.language_changed_cleared, Toast.LENGTH_SHORT).show()
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
                    } else {
                        Log.d("SettingsActivity", "Another crash point...")
                    }
                } else {
                    Log.d("SettingsActivity", "Not finished, so we might crash here!")
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
        }
    }
}