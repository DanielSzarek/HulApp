package pl.kamilszustak.hulapp.domain.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddMapPointRequestBody(
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
)