package pl.kamilszustak.hulapp.util

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import pl.kamilszustak.hulapp.domain.model.LocationPoint

fun Location.toLocationPoint(): LocationPoint =
    LocationPoint(this.latitude, this.longitude)

fun Location.toLatLng(): LatLng =
    LatLng(this.latitude, this.longitude)