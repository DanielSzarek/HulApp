package pl.kamilszustak.hulapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "users",
    foreignKeys = [
        ForeignKey(
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Country::class,
            parentColumns = ["id"],
            childColumns = ["country_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
@JsonClass(generateAdapter = true)
@Parcelize
data class User(
    @ColumnInfo(name = "email")
    @Json(name = "email")
    var email: String,

    @ColumnInfo(name = "username")
    @Json(name = "username")
    var username: String,

    @ColumnInfo(name = "password")
    @Json(name = "password")
    var password: String?,

    @ColumnInfo(name = "name")
    @Json(name = "first_name")
    var name: String,

    @ColumnInfo(name = "surname")
    @Json(name = "last_name")
    var surname: String,

    @ColumnInfo(name = "city_id")
    @Json(name = "city")
    var cityId: Long?,

    @ColumnInfo(name = "country_id")
    @Json(name = "country")
    var countryId: Long?
) : DatabaseEntity() {

    @Transient
    val fullName: String = "$name $surname"
}