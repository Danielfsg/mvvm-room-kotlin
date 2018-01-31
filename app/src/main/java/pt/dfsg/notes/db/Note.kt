package pt.dfsg.notes.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import java.util.*


@Entity
@TypeConverters(DateConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "date_created") var dateCreated: Date = Date(),
    @ColumnInfo(name = "date_modified") var dateModified: Date = Date(),
    @ColumnInfo(name = "color") var color: Int = 0,
    @ColumnInfo(name = "reminder") var reminder: Date? = null,
    @ColumnInfo(name = "has_reminder") var hasReminder: Boolean = false,
    @ColumnInfo(name = "show_content") var showContent: Boolean = true
)
