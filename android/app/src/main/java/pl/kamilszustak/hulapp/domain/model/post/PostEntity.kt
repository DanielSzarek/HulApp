package pl.kamilszustak.hulapp.domain.model.post

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import pl.kamilszustak.hulapp.domain.model.entity.DbEntity
import java.util.*

@Entity(tableName = "posts")
@Parcelize
data class PostEntity(
    @ColumnInfo(name = "edit_date")
    var editDate: Date?,

    @ColumnInfo(name = "author_id")
    val authorId: Long,

    @ColumnInfo(name = "content")
    val content: String
) : DbEntity()