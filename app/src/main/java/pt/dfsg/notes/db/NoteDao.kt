package pt.dfsg.notes.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
@TypeConverters(DateConverter::class)
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun allNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteById(id: String): LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun update(note: Note)
}