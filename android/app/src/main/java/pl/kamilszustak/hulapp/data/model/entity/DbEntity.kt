package pl.kamilszustak.hulapp.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

abstract class DbEntity : Parcelable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long = 0
}