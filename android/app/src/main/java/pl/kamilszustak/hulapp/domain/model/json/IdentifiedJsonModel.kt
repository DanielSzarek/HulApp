package pl.kamilszustak.hulapp.domain.model.json

import com.squareup.moshi.Json

abstract class IdentifiedJsonModel {
    @Json(name = "id")
    var id: Long = 0
}