package pt.dfsg.notes.listnotes

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Context
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.share
import pt.dfsg.notes.db.AppDatabase
import pt.dfsg.notes.db.Note


class NoteListViewModel constructor(app: Application) : AndroidViewModel(app) {

    private var noteList: LiveData<List<Note>>? = null
    private var appDatabase: AppDatabase? = null

    init {
        appDatabase = AppDatabase.getDatabase(app)
        noteList = appDatabase?.noteDao()?.allNotes()
    }

//    fun shareNote(note: Note) {
////        val shareIntent = Intent(Intent.ACTION_SEND)
////        shareIntent.type = "text/plain"
////        shareIntent.putExtra(Intent.EXTRA_TEXT, note.content)
////        val intent = Intent.createChooser(shareIntent, "Share note using")
////        startActivity(getApplication(), intent, null)
//
//    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return noteList
    }

    fun deleteNoteAnko(note: Note) {
        doAsync { appDatabase?.noteDao()?.deleteNote(note) }
    }

//    fun deleteNote(note: Note) {
//        DeleteAsyncTask(appDatabase).execute(note)
//    }
//
//    class DeleteAsyncTask constructor(private var db: AppDatabase?) :
//        AsyncTask<Note, Void, Void>() {
//
//        override fun doInBackground(vararg params: Note): Void? {
//            db?.noteDao()?.deleteNote(params[0])
//            return null
//        }
//    }
}