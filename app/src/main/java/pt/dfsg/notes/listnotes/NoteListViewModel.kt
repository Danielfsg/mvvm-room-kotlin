package pt.dfsg.notes.listnotes

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.doAsync
import pt.dfsg.notes.db.AppDatabase
import pt.dfsg.notes.db.Note
import pt.dfsg.notes.notification.AlarmReceiver
import pt.dfsg.notes.utils.ALERT_TEXT
import pt.dfsg.notes.utils.ALERT_TITLE


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

    fun deleteNoteAnko(note: Note) {
        doAsync { appDatabase?.noteDao()?.deleteNote(note) }
    }

    fun updateNoteAnko(note: Note) {
        doAsync { appDatabase?.noteDao()?.update(note) }
    }


    fun setAlarm(context: Context, alertTime: Long, title: String, body: String) {
        val alertIntent = Intent(context, AlarmReceiver::class.java)
        alertIntent.putExtra(ALERT_TITLE, title)
        alertIntent.putExtra(ALERT_TEXT, body)

        val manager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.set(
            AlarmManager.RTC_WAKEUP,
            alertTime,
            PendingIntent.getBroadcast(
                context, 1, alertIntent,
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