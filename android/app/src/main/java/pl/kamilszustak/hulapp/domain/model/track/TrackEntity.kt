package pl.kamilszustak.hulapp.domain.model.track

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import pl.kamilszustak.hulapp.domain.model.entity.IdentifiedDatabaseEntity
import java.util.*

@Entity(tableName = "tracks")
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
) : IdentifiedDatabaseEntity()