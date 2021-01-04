package uk.ac.aber.dcs.cs31620.phrasepad.util

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable

/**
 * Receiver class for broadcast intents.
 *
 * Thanks to StackOverflow for some guidance on this one:
 * https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android
 *
 * @see [BroadcastReceiver]
 */
class NotificationScheduler : BroadcastReceiver() {

    companion object {
        var id = "1"
        var notification = "notification"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification =
            intent?.getParcelableExtra<Parcelable>(notification) as Notification
        val id: Int = intent.getIntExtra(id, 0)
        notificationManager.notify(id, notification)
    }
}