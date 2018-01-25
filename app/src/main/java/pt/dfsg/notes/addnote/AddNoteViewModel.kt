package pt.dfsg.notes.addnote

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.os.AsyncTask
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import pt.dfsg.notes.db.AppDatabase
import pt.dfsg.notes.db.Note

class AddNoteViewModel constructor(app: Application) : AndroidViewModel(app) {

    private var appDatabase: AppDatabase? = null

    init {
        appDatabase = AppDatabase.getDatabase(app)
    }

    fun addNoteAnko(note:Note){
        doAsync { appDatabase?.noteDao()?.insertNote(note) }
    }

//    fun addNote(note: Note) {
//        AddAsyncTask(appDatabase).execute(note)
//    }
//
//    class AddAsyncTask constructor(private var db: AppDatabase?) : AsyncTask<Note, Void, Void>() {
//
//        override fun doInBackground(vararg params: Note): Void? {
//            db?.noteDao()?.insertNote(params[0])
//            return null
//        }
//    }



}