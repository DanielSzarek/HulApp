package pl.kamilszustak.hulapp.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
class Track(
    @Json(name = "time_start")
    var startDate: Date = Date(),

    @Json(name = "time_finish")
    var endDate: Date = Date(),

    @Json(name = "duration")
    var duration: Long = 0,

    @Json(name = "track_length")
    var distance: Double = 0.0
)