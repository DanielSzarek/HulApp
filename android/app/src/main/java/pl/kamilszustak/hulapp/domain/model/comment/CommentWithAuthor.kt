package pl.kamilszustak.hulapp.domain.model.comment

import pl.kamilszustak.hulapp.domain.model.User
import java.util.*

data class CommentWithAuthor(
    val id: Long,
    val creationDate: Date,
    val editDate: Date?,
    val postId: Long,
    val author: User,
    val content: String,
    val isMine: Boolean
) {
    val isEdited: Boolean
        get() = (editDate != null)
}