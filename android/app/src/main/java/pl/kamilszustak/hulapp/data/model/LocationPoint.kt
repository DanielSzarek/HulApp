package pl.kamilszustak.hulapp.data.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationPoint(
    val latitude: Double,
    val longtitue: Double
) : Parcelable {

    fun toLatLng(): LatLng = LatLng(this.latitude, this.longtitue)
}