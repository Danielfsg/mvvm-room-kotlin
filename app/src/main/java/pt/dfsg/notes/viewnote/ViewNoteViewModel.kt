package pt.dfsg.notes.viewnote

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import pt.dfsg.notes.db.AppDatabase
import pt.dfsg.notes.db.Note

class ViewNoteViewModel constructor(id:String,app:Application) :AndroidViewModel(app) {

    private var appDatabase: AppDatabase? = null
    private var note: LiveData<Note>?

    init {
        appDatabase = AppDatabase.getDatabase(app)
        note = appDatabase?.noteDao()?.getNoteById(id)
    }

    fun getNoteById(): LiveData<Note>? {
        return note
    }

}