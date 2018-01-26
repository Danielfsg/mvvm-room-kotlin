package pt.dfsg.notes.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pt.dfsg.notes.utils.ALERT_TEXT
import pt.dfsg.notes.utils.ALERT_TITLE

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val helper = NotificationHelper(context)
        val name = intent.getStringExtra(ALERT_TITLE)
        val body = intent.getStringExtra(ALERT_TEXT)

        helper.notify(
            1100,
            helper.getNotification(name, body)
        )
    }

}