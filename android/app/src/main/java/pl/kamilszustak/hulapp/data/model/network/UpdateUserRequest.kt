package pl.kamilszustak.hulapp.data.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateUserRequest(
    @Json(name = "first_name")
    var name: String,

    @Json(name = "last_name")
    var surname: String,

    @Json(name = "city")
    var cityId: Long?,

    @Json(name = "country")
    var countryId: Long?
)