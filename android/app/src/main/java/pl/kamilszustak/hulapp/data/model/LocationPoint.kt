package pl.kamilszustak.hulapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationPoint(
    val latitude: Double,
    val longtitue: Double
) : Parcelable