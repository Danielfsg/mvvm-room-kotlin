package pt.dfsg.notes.listnotes

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import pt.dfsg.notes.db.AppDatabase
import pt.dfsg.notes.db.Note
import pt.dfsg.notes.notification.AlarmReceiver
import pt.dfsg.notes.utils.ALERT_TEXT
import pt.dfsg.notes.utils.ALERT_TITLE
import pt.dfsg.notes.utils.ID


class NoteListViewModel constructor(app: Application) : AndroidViewModel(app) {

    private var noteList: LiveData<List<Note>>? = null
    private var appDatabase: AppDatabase? = null

    init {
        appDatabase = AppDatabase.getDatabase(app)
        noteList = appDatabase?.noteDao()?.allNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return noteList
    }

    fun deleteNote(note: Note) {
        async(CommonPool) { appDatabase?.noteDao()?.deleteNote(note) }.start()
    }

    fun updateNote(note: Note) {
        async(CommonPool) { appDatabase?.noteDao()?.update(note) }.start()
    }

    fun setAlarm(context: Context, alertTime: Long, title: String, body: String, id: String) {
        val alertIntent = Intent(context, AlarmReceiver::class.java)
        alertIntent.putExtra(ALERT_TITLE, title)
        alertIntent.putExtra(ALERT_TEXT, body)
        alertIntent.putExtra(ID, id)

        val manager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.set(
            AlarmManager.RTC_WAKEUP,
            alertTime,
            PendingIntent.getBroadcast(
                context,
                id.toInt(),
                alertIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    /* old shared method
    fun shareNote(note: Note) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, note.content)
        val intent = Intent.createChooser(shareIntent, "Share note using")
        startActivity(getApplication(), intent, null)
    }
    */

    /* old delete note asynctask
    fun deleteNote(note: Note) {
        DeleteAsyncTask(appDatabase).execute(note)
//        doAsync { appDatabase?.noteDao()?.deleteNote(note) }
//        doAsync { appDatabase?.noteDao()?.update(note) }
    }

    class DeleteAsyncTask constructor(private var db: AppDatabase?) :
        AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            db?.noteDao()?.deleteNote(params[0])
            return null
        }
    }
    */
}