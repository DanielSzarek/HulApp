package pl.kamilszustak.hulapp.domain.model.point

import androidx.annotation.DrawableRes
import pl.kamilszustak.hulapp.R

enum class MapPointRating(
    val value: Int,
    @DrawableRes val markerIconResource: Int
) {
    RATING_1(1, R.drawable.icon_marker_1),
    RATING_2(2, R.drawable.icon_marker_2),
    RATING_3(3, R.drawable.icon_marker_3),
    RATING_4(4, R.drawable.icon_marker_4),
    RATING_5(5, R.drawable.icon_marker_5);

    companion object {
        val default: MapPointRating
            get() = RATING_1

        fun ofValue(value: Int): MapPointRating =
            MapPointRating.values()
                .find { it.value == value }
                ?: this.default
    }
}