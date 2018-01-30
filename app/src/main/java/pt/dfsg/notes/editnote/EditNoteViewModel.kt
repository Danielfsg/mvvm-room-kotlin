package pt.dfsg.notes.editnote

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import pt.dfsg.notes.db.AppDatabase
import pt.dfsg.notes.db.Note

class EditNoteViewModel constructor(id: String, app: Application) : AndroidViewModel(app) {

    private var appDatabase: AppDatabase? = null
    private var note: LiveData<Note>?

    init {
        appDatabase = AppDatabase.getDatabase(app)
        note = appDatabase?.noteDao()?.getNoteById(id)
    }

    fun getNoteById(): LiveData<Note>? {
        return note
    }

    fun updateNote(note: Note) {
        async(CommonPool) { appDatabase?.noteDao()?.update(note) }.start()
    }

    class Factory constructor(private var app: Application, private var params: String) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EditNoteViewModel(params, app) as T
        }
    }

    /* old update async task
fun updateNote(note: Note) {
    doAsync { appDatabase?.noteDao()?.update(note) }
    UpdateAsyncTask(appDatabase).execute(note)
}

class UpdateAsyncTask constructor(private var db: AppDatabase?) : AsyncTask<Note, Void, Void>() {

    override fun doInBackground(vararg params: Note): Void? {
        db?.noteDao()?.update(params[0])
        return null
    }
}
*/

}
