package pl.kamilszustak.hulapp.data.model.json

import com.squareup.moshi.Json

abstract class JsonModel {
    @Json(name = "id")
    var id: Long = 0
}