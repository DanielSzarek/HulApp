package pl.kamilszustak.hulapp.data.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PasswordResetRequest(
    @Json(name = "email")
    val email: String
)