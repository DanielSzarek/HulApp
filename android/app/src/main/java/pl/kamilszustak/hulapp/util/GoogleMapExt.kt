package pl.kamilszustak.hulapp.util

import com.google.android.gms.maps.model.PolylineOptions

fun polylineOptions(block: PolylineOptions.() -> Unit): PolylineOptions {
    return PolylineOptions().apply {
        block()
    }
}