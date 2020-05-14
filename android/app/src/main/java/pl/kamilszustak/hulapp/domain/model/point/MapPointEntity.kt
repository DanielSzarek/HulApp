package pl.kamilszustak.hulapp.domain.model.point

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import pl.kamilszustak.hulapp.domain.model.DatabaseEntity

@Entity(tableName = "map_points")
@Parcelize
data class MapPointEntity(
    @ColumnInfo(name = "author_id")
    val authorId: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "rating")
    val rating: Int,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double
) : DatabaseEntity()