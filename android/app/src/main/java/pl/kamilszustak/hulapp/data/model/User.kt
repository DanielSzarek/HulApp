package pl.kamilszustak.hulapp.data.model

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

    @ColumnInfo(name = "city")
    @Json(name = "city")
    var city: String?,

    @ColumnInfo(name = "country")
    @Json(name = "country")
    var country: String?
) : DatabaseEntity()