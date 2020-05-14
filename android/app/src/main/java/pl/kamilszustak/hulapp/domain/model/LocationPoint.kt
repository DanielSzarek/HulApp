package pl.kamilszustak.hulapp.domain.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationPoint(
    val latitude: Double,
    val longitue: Double
) : Parcelable {

    fun toLatLng(): LatLng = LatLng(this.latitude, this.longitue)
}