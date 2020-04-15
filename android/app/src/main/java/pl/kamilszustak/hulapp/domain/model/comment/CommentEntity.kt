package pl.kamilszustak.hulapp.domain.model.comment

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import pl.kamilszustak.hulapp.domain.model.entity.DbEntity
import java.util.*

@Entity(tableName = "comments")
@Parcelize
data class CommentEntity(
    @ColumnInfo(name = "post_id")
    val postId: Long,

    @ColumnInfo(name = "edit_date")
    val editDate: Date?,

    @ColumnInfo(name = "author_id")
    val authorId: Long,

    @ColumnInfo(name = "content")
    val content: String
) : DbEntity()