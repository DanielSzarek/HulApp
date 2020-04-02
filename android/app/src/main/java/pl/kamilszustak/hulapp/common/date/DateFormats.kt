package pl.kamilszustak.hulapp.common.date

import java.text.SimpleDateFormat
import java.util.*

object DateFormats {
    private val locale: Locale = Locale.forLanguageTag("PL")

    val fullDateFormat: SimpleDateFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        locale
    )

    val dateFormat: SimpleDateFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm",
        locale
    )
}