package uk.ac.aber.dcs.cs31620.phrasepad.util

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable

class NotificationScheduler : BroadcastReceiver() {

    companion object {
        var NOTIFICATION_ID = "1"
        var NOTIFICATION = "notification"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification =
            intent?.getParcelableExtra<Parcelable>(NOTIFICATION) as Notification
        val id: Int = intent.getIntExtra(NOTIFICATION_ID, 0)
        notificationManager.notify(id, notification)
    }
}