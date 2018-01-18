package pt.dfsg.notes.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(Note::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private var dbInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "note-db"
                ).build()
            }
            return dbInstance
        }
    }

}
