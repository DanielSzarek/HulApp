package pl.kamilszustak.hulapp.data.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangePasswordRequestBody(
    @Json(name = "current_password")
    val currentPassword: String,

    @Json(name = "new_password")
    val newPassword: String
)