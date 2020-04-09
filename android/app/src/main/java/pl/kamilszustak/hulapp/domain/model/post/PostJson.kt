package pl.kamilszustak.hulapp.domain.model.post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.json.JsonModel
import java.util.*

@JsonClass(generateAdapter = true)
data class PostJson(
    @Json(name = "mod_date")
    var editDate: Date?,

    @Json(name = "author")
    var author: User,

    @Json(name = "text")
    var content: String
) : JsonModel()