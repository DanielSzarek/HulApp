package pl.kamilszustak.hulapp.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

abstract class DatabaseEntity : Parcelable {
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "created_at")
    @Transient
    var createdAt: Date = Date()

    @ColumnInfo(name = "updated_at")
    @Transient
    var updatedAt: Date = Date()
}