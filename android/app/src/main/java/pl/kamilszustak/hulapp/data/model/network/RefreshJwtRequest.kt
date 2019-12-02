package pl.kamilszustak.hulapp.data.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RefreshJwtRequest(
    @Json(name = "refresh")
    val refreshToken: String
)