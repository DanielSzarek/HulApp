package pl.kamilszustak.hulapp.domain.model.json

import com.squareup.moshi.Json
import java.util.*

abstract class JsonModel : IdentifiedJsonModel() {
    @Json(name = "add_date")
    var createdAt: Date = Date()
}