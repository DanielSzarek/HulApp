package pl.kamilszustak.hulapp.domain.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddCommentRequestBody(
    @Json(name = "post")
    val postId: Long,

    @Json(name = "text")
    val content: String
)