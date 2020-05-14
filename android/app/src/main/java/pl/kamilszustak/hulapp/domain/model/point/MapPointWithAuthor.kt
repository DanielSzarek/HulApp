package pl.kamilszustak.hulapp.domain.model.point

import androidx.room.Embedded
import androidx.room.Relation
import pl.kamilszustak.hulapp.domain.model.User

data class MapPointWithAuthor(
    @Embedded
    val mapPoint: MapPointEntity,

    @Relation(
        entity = User::class,
        parentColumn = "author_id",
        entityColumn = "id"
    )
    val author: User
)