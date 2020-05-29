package pl.kamilszustak.hulapp.domain.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateJwtRequest(
    @Json(name = "email")
    val email: String,

    @Json(name = "password")
    val password: String
)