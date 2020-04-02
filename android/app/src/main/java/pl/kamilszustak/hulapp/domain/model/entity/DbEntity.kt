package pl.kamilszustak.hulapp.domain.model.entity

import androidx.room.ColumnInfo
import java.util.*

abstract class DbEntity : IdentifiedDatabaseEntity() {
    @ColumnInfo(name = "created_at")
    var createdAt: Date = Date()
}