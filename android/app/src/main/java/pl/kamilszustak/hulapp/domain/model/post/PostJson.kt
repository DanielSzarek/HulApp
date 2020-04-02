package pl.kamilszustak.hulapp.domain.model.post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.json.JsonModel

@JsonClass(generateAdapter = true)
data class PostJson(
    @Json(name = "author")
    var author: User,

    @Json(name = "text")
    var content: String,

    @Json(name = "published")
    var isPublished: Boolean
) : JsonModel()