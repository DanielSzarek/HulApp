package pl.kamilszustak.hulapp.data.model.track

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.android.parcel.Parcelize
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.model.entity.DbEntity
import java.util.*

@Entity(
    tableName = "tracks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            childColumns = ["user_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Parcelize
data class TrackEntity(
    @ColumnInfo(name = "start_date")
    val startDate: Date,

    @ColumnInfo(name = "end_date")
    val endDate: Date,

    @ColumnInfo(name = "duration")
    val duration: Long,

    @ColumnInfo(name = "distance")
    val distance: Double,

    @ColumnInfo(name = "user_id")
    val userId: Long
) : DbEntity()