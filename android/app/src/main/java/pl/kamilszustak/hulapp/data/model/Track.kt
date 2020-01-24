package pl.kamilszustak.hulapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "tracks")
@JsonClass(generateAdapter = true)
@Parcelize
class Track(
    @ColumnInfo(name = "start_date")
    @Json(name = "time_start")
    var startDate: Date = Date(),

    @ColumnInfo(name = "end_date")
    @Json(name = "time_finish")
    var endDate: Date = Date(),

    @ColumnInfo(name = "duration")
    @Json(name = "duration")
    var duration: Long = 0,

    @ColumnInfo(name = "distance")
    @Json(name = "track_length")
    var distance: Double = 0.0
) : DatabaseEntity()