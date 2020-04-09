package pl.kamilszustak.hulapp.domain.model.entity

import androidx.room.ColumnInfo
import java.util.*

abstract class DbEntity : IdentifiedDatabaseEntity() {
    @ColumnInfo(name = "creation_date")
    var creationDate: Date = Date()
}