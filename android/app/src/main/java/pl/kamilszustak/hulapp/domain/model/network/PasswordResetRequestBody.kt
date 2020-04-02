package pl.kamilszustak.hulapp.domain.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PasswordResetRequestBody(
    @Json(name = "email")
    val email: String
)

