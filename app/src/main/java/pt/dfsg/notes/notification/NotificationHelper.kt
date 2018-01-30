package pt.dfsg.notes.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import pt.dfsg.notes.R
import pt.dfsg.notes.utils.ID
import pt.dfsg.notes.utils.PRIMARY_CHANNEL
import pt.dfsg.notes.viewnote.ViewNoteActivity

class NotificationHelper(context: Context) : ContextWrapper(context) {

    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        val chan1 = NotificationChannel(
            PRIMARY_CHANNEL,
            getString(R.string.noti_channel_default), NotificationManager.IMPORTANCE_DEFAULT
        )
        chan1.lightColor = Color.GREEN
        chan1.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager.createNotificationChannel(chan1)
    }

    private fun pendingIntent(id: String): PendingIntent {
        val intent = Intent(applicationContext, ViewNoteActivity::class.java)
        intent.putExtra(ID, id)
        return PendingIntent.getActivity(applicationContext, 0, intent, 0)

    }

    fun getNotification(title: String, body: String, id: String): Notification.Builder {
        return Notification.Builder(applicationContext, PRIMARY_CHANNEL)
            .setContentTitle(title)
            .setContentText(body)
            .setSubText("Tap to open note.")
            .setSmallIcon(smallIcon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent(id))
    }

    fun notify(id: Int, notification: Notification.Builder) {
        manager.notify(id, notification.build())
    }

    private val smallIcon: Int
        get() = android.R.drawable.ic_dialog_alert
}