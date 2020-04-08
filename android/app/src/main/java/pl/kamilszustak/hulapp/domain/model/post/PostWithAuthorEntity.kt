package pl.kamilszustak.hulapp.domain.model.post

import androidx.room.Embedded
import androidx.room.Relation
import pl.kamilszustak.hulapp.domain.model.User

data class PostWithAuthorEntity(
    @Embedded
    val post: PostEntity,

    @Relation(
        entity = User::class,
        parentColumn = "author_id",
        entityColumn = "id"
    )
    val author: User
)