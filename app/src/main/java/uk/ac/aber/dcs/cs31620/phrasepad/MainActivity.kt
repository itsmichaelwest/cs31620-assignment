package uk.ac.aber.dcs.cs31620.phrasepad

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ActivityMainBinding
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.ToolbarHeroBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases.PhraseAddFragment
import uk.ac.aber.dcs.cs31620.phrasepad.ui.settings.SetLanguagesFragment
import uk.ac.aber.dcs.cs31620.phrasepad.ui.settings.SettingsActivity
import uk.ac.aber.dcs.cs31620.phrasepad.util.NotificationScheduler
import java.util.*

/**
 * Main app Activity. Handles setting up the toolbar and navigation, and registering
 * an [OnSharedPreferenceChangeListener] to listen for language preference changes.
 *
 * @since 1.0
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listener: OnSharedPreferenceChangeListener

    override fun onStart() {
        super.onStart()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
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

        // Handle home screen shortcut to Add Phrase
        if ("ADDPHRASE" == intent.action) {
            showAddPhraseFragment(findViewById(R.id.layout))
            intent.action = ""
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
        binding.toolbar.toolbar.setOnMenuItemClickListener { menuItem ->
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
        val destinationLanguage = Language(
            Locale(
                PreferenceManager.getDefaultSharedPreferences(
                    applicationContext
                ).getString("dest_lang", "eng")!!
            )
        )
        val langFlag =
            findViewById<ImageView>(R.id.langFlag) // We need to find the view by id for some reason, binding doesn't work
        binding.toolbar.toolbarTitle.text = destinationLanguage.getPreferredName(applicationContext)
        langFlag.setImageDrawable(destinationLanguage.getFlag(applicationContext))

        // Set up notification
        var learningNotif =
            PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean(
                "daily_quiz_notification",
                false
            )
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "1",
            resources.getString(R.string.notifications_header),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        if (learningNotif)
            setUpScheduledNotification(false)
        else
            setUpScheduledNotification(true)


        // Listen to preference changes for the App Bar flag/language name
        listener =
            OnSharedPreferenceChangeListener { sharedPreferences, key ->
                when (key) {
                    "dest_lang" -> {
                        val language =
                            Language(Locale(sharedPreferences.getString("dest_lang", "eng")!!))
                        binding.toolbar.toolbarTitle.text =
                            language.getPreferredName(applicationContext)
                        binding.toolbar.langFlag.setImageDrawable(
                            Language(
                                Locale(
                                    sharedPreferences.getString(
                                        "dest_lang",
                                        "eng"
                                    )!!
                                )
                            ).getFlag(applicationContext)
                        )
                    }
                    "always_dev_lang" -> {
                        binding.toolbar.toolbarTitle.text =
                            destinationLanguage.getPreferredName(applicationContext)
                    }
                    "daily_quiz_notification" -> {
                        learningNotif = !learningNotif
                        if (learningNotif)
                            setUpScheduledNotification(false)
                        else
                            setUpScheduledNotification(true)
                    }
                }
            }
        PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .registerOnSharedPreferenceChangeListener(
                listener
            )

        // Set up bottom navigation
        val bottomNavigation = binding.bottomNavigation
        val bottomNavigationController = findNavController(R.id.navigation_fragment_host)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bnav_phrases -> {
                    binding.floatingActionButton.show()
                    bottomNavigationController.navigate(R.id.bnav_phrases)
                    true
                }
                R.id.bnav_quiz -> {
                    binding.floatingActionButton.hide()
                    bottomNavigationController.navigate(R.id.bnav_quiz)
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Show [PhraseAddFragment]. This is set using the onClick parameter in the Activity's XML.
     * @since 1.0
     */
    fun showAddPhraseFragment(view: View) {
        val fragment = PhraseAddFragment()
        fragment.show(supportFragmentManager, "add_phrase_fragment")
    }

    /**
     * Show [SetLanguagesFragment] upon first run of the app, where the language preferences will
     * be null.
     * @since 1.0
     */
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

    /**
     * Set up or cancel a scheduled notification. Currently the notification is scheduled to fire
     * at 12:00 noon. This function is quite rough and notification functionality is likely to be
     * buggy at best.
     *
     * Thanks to StackOverflow for some guidance on this one:
     * https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android
     *
     * @param willCancel Set to false if the notification is to not be cancelled, true if it will be.
     * @since 1.0
     */
    private fun setUpScheduledNotification(willCancel: Boolean) {
        val builder = Notification.Builder(this, "1")
        builder.setContentTitle(resources.getString(R.string.notification_title))
        builder.setContentText(resources.getString(R.string.notification_content))
        builder.setSmallIcon(R.drawable.ic_notification_icon)
        builder.setContentIntent(
            PendingIntent.getActivity(
                applicationContext, 0, Intent(
                    this,
                    MainActivity::class.java
                ), PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        val notificationIntent = Intent(this, NotificationScheduler::class.java)
        notificationIntent.putExtra(NotificationScheduler.id, 1)
        notificationIntent.putExtra(NotificationScheduler.notification, builder.build())
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)

        val manager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (!willCancel) {
            manager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } else {
            manager.cancel(pendingIntent)
        }
    }
}