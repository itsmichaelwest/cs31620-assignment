package uk.ac.aber.dcs.cs31620.phrasepad

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.toolbar_hero.view.*
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ActivityMainBinding
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ToolbarHeroBinding
import uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases.AddPhraseFragment
import uk.ac.aber.dcs.cs31620.phrasepad.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var themeListener: SharedPreferences.OnSharedPreferenceChangeListener
    public lateinit var layout: CoordinatorLayout

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
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val destLangCode = sharedPreferences.getString("dest_lang", "cy")
        val flagResId = resources.getIdentifier(destLangCode.toString(), "drawable", packageName)
        toolbar.lang_flag.setImageDrawable(ResourcesCompat.getDrawable(resources, flagResId, null))

        setSupportActionBar(toolbar)

        val bottomNavigation = binding.bottomNavigation
        val bottomNavigationController = findNavController(R.id.navigation_fragment_host)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.bnav_phrases, R.id.bnav_search, R.id.bnav_quiz
        ))

        setupActionBarWithNavController(bottomNavigationController, appBarConfiguration)
        bottomNavigation.setupWithNavController(bottomNavigationController)

        layout = findViewById(R.id.layout)
    }

    fun showAddPhraseFragment(view: View) {
        var fragment = AddPhraseFragment()
        fragment.show(supportFragmentManager, "add_phrase_fragment")
    }
}