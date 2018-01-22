package pt.dfsg.notes.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.content.res.Resources
import android.graphics.Color
import java.util.*


@Entity
@TypeConverters(DateConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "date") var date: Date = Date(),
    @ColumnInfo(name = "color") var color: Int = Color.parseColor("#ffffff")
)
