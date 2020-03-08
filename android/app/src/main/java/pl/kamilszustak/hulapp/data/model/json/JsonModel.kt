package pl.kamilszustak.hulapp.data.model.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
abstract class JsonModel {
    @Json(name = "id")
    var id: Long = 0
}