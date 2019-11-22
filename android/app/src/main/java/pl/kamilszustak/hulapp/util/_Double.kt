package pl.kamilszustak.hulapp.util

import kotlin.math.round

fun Double.round(decimalPlaces: Int = 2): Double {
    var multiplier = 1.0
    repeat(decimalPlaces) {
        multiplier *= 10
    }

    return round(this * multiplier) / multiplier
}