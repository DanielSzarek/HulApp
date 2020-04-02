package pl.kamilszustak.hulapp.domain.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

abstract class IdentifiedDatabaseEntity : Parcelable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long = 0
}