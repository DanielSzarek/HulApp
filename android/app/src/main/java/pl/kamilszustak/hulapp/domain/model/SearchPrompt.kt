package pl.kamilszustak.hulapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "search_prompts")
data class SearchPrompt(
    @ColumnInfo(name = "text")
    val text: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "date")
    var date: Date = Date()
}