package pt.dfsg.notes.listnotes

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import pt.dfsg.notes.db.AppDatabase
import pt.dfsg.notes.db.Note

class NoteListViewModel constructor(app: Application) : AndroidViewModel(app) {

    private var noteList: LiveData<List<Note>>? = null
    private var appDatabase: AppDatabase? = null

    init {
        appDatabase = AppDatabase.getDatabase(app)
        noteList = appDatabase?.noteDao()?.allNotes()
    }

    fun getAllNote(): LiveData<List<Note>>? {
        return noteList
    }

    fun deleteNote(note: Note) {
        DeleteAsyncTask(appDatabase).execute(note)
    }

    class DeleteAsyncTask constructor(private var db: AppDatabase?) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            db?.noteDao()?.deleteNote(params[0])
            return null
        }
    }
}