package pl.kamilszustak.hulapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "countries")
@JsonClass(generateAdapter = true)
@Parcelize
data class Country(
    @ColumnInfo(name = "name")
    @Json(name = "name")
    var name: String
) : DatabaseEntity()