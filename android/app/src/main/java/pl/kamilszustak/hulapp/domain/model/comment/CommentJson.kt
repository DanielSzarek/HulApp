package pl.kamilszustak.hulapp.domain.model.comment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.json.JsonModel
import java.util.*

@JsonClass(generateAdapter = true)
data class CommentJson(
    @Json(name = "post")
    val postId: Long,

    @Json(name = "mod_date")
    val editDate: Date?,

    @Json(name = "author")
    val author: User,

    @Json(name = "text")
    val content: String
) : JsonModel()