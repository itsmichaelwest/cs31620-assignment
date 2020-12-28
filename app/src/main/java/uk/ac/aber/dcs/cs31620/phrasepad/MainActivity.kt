package uk.ac.aber.dcs.cs31620.phrasepad

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ActivityMainBinding
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ToolbarHeroBinding
import uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases.AddPhraseFragment
import uk.ac.aber.dcs.cs31620.phrasepad.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var themeListener: SharedPreferences.OnSharedPreferenceChangeListener

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbarHeroBinding = ToolbarHeroBinding.inflate(layoutInflater)
        val toolbar = toolbarHeroBinding.toolbar
        toolbar.inflateMenu(R.menu.toolbar)

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

        // Set flag based on dest lang preference
        /*
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val destLangCode = sharedPreferences.getString("dest_lang", "")
        Log.d("MainActivity", destLangCode.toString())
        val flagResId = resources.getIdentifier(destLangCode.toString(), "drawable", packageName)
        val flagResImage = ResourcesCompat.getDrawable(resources, flagResId, null)
        toolbar.lang_flag.setImageDrawable(flagResImage)
         */

        setSupportActionBar(toolbar)

        val bottomNavigation = binding.bottomNavigation
        val bottomNavigationController = findNavController(R.id.navigation_fragment_host)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.bnav_phrases, R.id.bnav_search, R.id.bnav_quiz
        ))

        setupActionBarWithNavController(bottomNavigationController, appBarConfiguration)
        bottomNavigation.setupWithNavController(bottomNavigationController)
    }

    fun showAddPhraseFragment(view: View) {
        var fragment = AddPhraseFragment()
        fragment.show(supportFragmentManager, "add_phrase_fragment")
    }
}