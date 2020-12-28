package uk.ac.aber.dcs.cs31620.phrasepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ActivityMainBinding
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ToolbarHeroBinding
import uk.ac.aber.dcs.cs31620.phrasepad.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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