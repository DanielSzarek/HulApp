package pl.kamilszustak.hulapp.data.model.track

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pl.kamilszustak.hulapp.data.model.json.JsonModel
import java.util.*

@JsonClass(generateAdapter = true)
class TrackJson(
    @Json(name = "time_start")
    var startDate: Date,

    @Json(name = "time_finish")
    var endDate: Date,

    @Json(name = "duration")
    var duration: Long,

    @Json(name = "track_length")
    var distance: Double,

    @Json(name = "user")
    var userId: Long
) : JsonModel()