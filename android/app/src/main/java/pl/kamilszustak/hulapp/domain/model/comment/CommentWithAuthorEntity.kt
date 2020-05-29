package pl.kamilszustak.hulapp.domain.model.comment

import androidx.room.Embedded
import androidx.room.Relation
import pl.kamilszustak.hulapp.domain.model.User

data class CommentWithAuthorEntity(
    @Embedded
    val comment: CommentEntity,

    @Relation(
        entity = User::class,
        parentColumn = "author_id",
        entityColumn = "id"
    )
    val author: User
)