package pl.kamilszustak.hulapp.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo

abstract class DbEntity : Parcelable {
    @ColumnInfo(name = "id")
    var id: Long = 0
}