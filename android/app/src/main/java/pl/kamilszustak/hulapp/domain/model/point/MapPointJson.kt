package pl.kamilszustak.hulapp.domain.model.point

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.json.IdentifiedJsonModel

@JsonClass(generateAdapter = true)
data class MapPointJson(
    @Json(name = "author")
    val author: User,

    @Json(name = "name")
    val name: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "rating")
    val rating: Int,

    @Json(name = "latitude")
    val latitude: Double,

    @Json(name = "longitude")
    val longitude: Double
) : IdentifiedJsonModel()