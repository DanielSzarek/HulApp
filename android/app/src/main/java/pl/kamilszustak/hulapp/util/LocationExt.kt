package pl.kamilszustak.hulapp.util

import android.location.Location
import pl.kamilszustak.hulapp.data.model.LocationPoint

fun Location.toLocationPoint(): LocationPoint =
    LocationPoint(this.latitude, this.longitude)