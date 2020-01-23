package pl.kamilszustak.hulapp.util

import kotlin.math.round

fun Double.round(decimalPlaces: Int = 2): Double {
    var multiplier = 1.0
    repeat(decimalPlaces) {
        multiplier *= 10
    }

    return round(this * multiplier) / multiplier
}

fun Long.asTimeString(): String {
    return String.format(
        "%02d:%02d:%02d",
        this / 3600,
        (this % 3600) / 60,
        (this % 60)
    )
}