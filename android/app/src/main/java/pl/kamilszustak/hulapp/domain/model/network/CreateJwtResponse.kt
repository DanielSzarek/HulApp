package pl.kamilszustak.hulapp.domain.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateJwtResponse(
    @Json(name = "refresh")
    val refreshToken: String,

    @Json(name = "access")
    val accessToken: String
)