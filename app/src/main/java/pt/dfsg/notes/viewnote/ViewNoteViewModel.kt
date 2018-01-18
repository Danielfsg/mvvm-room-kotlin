package pt.dfsg.notes.viewnote

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pt.dfsg.notes.db.AppDatabase
import pt.dfsg.notes.db.Note

class ViewNoteViewModel constructor(id: String, app: Application) : AndroidViewModel(app) {

    private var appDatabase: AppDatabase? = null
    private var note: LiveData<Note>?

    init {
        appDatabase = AppDatabase.getDatabase(app)
        note = appDatabase?.noteDao()?.getNoteById(id)
    }

    fun getNoteById(): LiveData<Note>? {
        return note
    }

    class Factory constructor(private var app: Application, private var params: String) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ViewNoteViewModel(params, app) as T
        }
    }

}