package pl.kamilszustak.hulapp.domain.model.point

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import pl.kamilszustak.hulapp.domain.model.User

@Parcelize
data class MapPoint(
    val id: Long,
    val author: User,
    val name: String,
    val description: String,
    val rating: MapPointRating,
    val isMine: Boolean,
    val location: LatLng
) : Parcelable