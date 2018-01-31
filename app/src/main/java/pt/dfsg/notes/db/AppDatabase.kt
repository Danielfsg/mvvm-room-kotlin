package pt.dfsg.notes.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import pt.dfsg.notes.utils.DB_NAME

@Database(entities = [(Note::class)], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private var dbInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return dbInstance
        }
    }

}
