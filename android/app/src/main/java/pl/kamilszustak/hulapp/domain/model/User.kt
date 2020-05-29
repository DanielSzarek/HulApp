package pl.kamilszustak.hulapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users")
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
    var countryId: Long?,

    @ColumnInfo(name = "profile_photo_url")
    @Json(name = "profile_img")
    var profilePhotoUrl: String? = null
) : DatabaseEntity() {

    val fullName: String
        get() = "$name $surname"
}