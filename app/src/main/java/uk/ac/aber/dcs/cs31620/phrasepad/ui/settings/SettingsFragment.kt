package uk.ac.aber.dcs.cs31620.phrasepad.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import uk.ac.aber.dcs.cs31620.phrasepad.R

// Boilerplate code to set preferences using the root preferences XML
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}