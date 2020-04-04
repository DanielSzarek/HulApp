package pl.kamilszustak.hulapp.domain.model.post

import pl.kamilszustak.hulapp.domain.model.User
import java.util.*

data class PostWithAuthor(
    val id: Long,
    val createdAt: Date,
    val content: String,
    val author: User,
    val isMine: Boolean
)