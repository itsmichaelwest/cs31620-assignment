package uk.ac.aber.dcs.cs31620.phrasepad

import android.content.Intent
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.toolbar_hero.view.*
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ActivityMainBinding
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ToolbarHeroBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases.PhraseAddFragment
import uk.ac.aber.dcs.cs31620.phrasepad.ui.settings.SetLanguagesFragment
import uk.ac.aber.dcs.cs31620.phrasepad.ui.settings.SettingsActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var heroTitleListener: OnSharedPreferenceChangeListener

    override fun onStart() {
        super.onStart()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when(sharedPreferences.getString("theme", "0")) {
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

        // Handle home screen shortcut to Add Phrase
        if ("ADDPHRASE".equals(intent.action)) {
            showAddPhraseFragment(findViewById(R.id.layout))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Define language if not set
        firstRunSetLanguage(findViewById(R.id.layout))

        // Set up toolbar
        val toolbarHeroBinding = ToolbarHeroBinding.inflate(layoutInflater)
        val toolbar = toolbarHeroBinding.toolbar
        toolbar.inflateMenu(R.menu.toolbar)
        setSupportActionBar(toolbar)

        // Settings menu listener
        binding.toolbar.toolbar.setOnMenuItemClickListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.tb_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // Set flag/language name based on preferences
        val destinationLanguage = Language(Locale(PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("dest_lang", "en")!!))
        var alwaysDevLang = PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean("always_dev_lang", false)
        val langFlag = findViewById<ImageView>(R.id.langFlag) // We need to find the view by id for some reason, binding doesn't work
        if (alwaysDevLang)
            binding.toolbar.toolbar.toolbar_title.text = destinationLanguage.getDeviceLangName()
        else
            binding.toolbar.toolbar.toolbar_title.text = destinationLanguage.getNativeName()
        langFlag.setImageDrawable(destinationLanguage.getFlag(applicationContext))

        // Listen to preference changes for the App Bar flag/language name
        heroTitleListener =
            OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == "dest_lang") {
                    alwaysDevLang = sharedPreferences.getBoolean("always_dev_lang", false)
                    if (alwaysDevLang)
                        binding.toolbar.toolbar.toolbar_title.text = Language(Locale(sharedPreferences.getString("dest_lang", "en")!!)).getDeviceLangName()
                    else
                        binding.toolbar.toolbar.toolbar_title.text = Language(Locale(sharedPreferences.getString("dest_lang", "en")!!)).getNativeName()
                    langFlag.setImageDrawable(Language(Locale(sharedPreferences.getString("dest_lang", "en")!!)).getFlag(applicationContext))
                }
                if (key == "always_dev_lang") {
                    alwaysDevLang = sharedPreferences.getBoolean("always_dev_lang", false)
                    if (alwaysDevLang) {
                        binding.toolbar.toolbar.toolbar_title.text = Language(Locale(sharedPreferences.getString("dest_lang", "en")!!)).getDeviceLangName()
                    } else {
                        binding.toolbar.toolbar.toolbar_title.text = Language(Locale(sharedPreferences.getString("dest_lang", "en")!!)).getNativeName()
                    }
                }
            }
        PreferenceManager.getDefaultSharedPreferences(applicationContext).registerOnSharedPreferenceChangeListener(heroTitleListener)

        // Set up bottom navigation
        val bottomNavigation = binding.bottomNavigation
        val bottomNavigationController = findNavController(R.id.navigation_fragment_host)
        bottomNavigation.setupWithNavController(bottomNavigationController)
    }

    // Show the Add Phrase fragment
    fun showAddPhraseFragment(view: View) {
        val fragment = PhraseAddFragment()
        fragment.show(supportFragmentManager, "add_phrase_fragment")
    }

    private fun firstRunSetLanguage(view: View) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val sourceLanguage = sharedPreferences.getString("source_lang", null)
        val destinationLanguage = sharedPreferences.getString("dest_lang", null)

        if (sourceLanguage == null || destinationLanguage == null) {
            val fragment = SetLanguagesFragment()
            fragment.isCancelable = false
            fragment.show(supportFragmentManager, "set_languages_fragment")
        }
    }
}