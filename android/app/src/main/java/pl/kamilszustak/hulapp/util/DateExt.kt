package pl.kamilszustak.hulapp.util

import java.util.*

fun Date.exactOrBefore(date: Date): Boolean {
    return this.time <= date.time
}

fun Date.exactOrAfter(date: Date): Boolean {
    return this.time >= date.time
}

fun today(): Date {
    val date = Date()

    val year = date.get(Calendar.YEAR)
    val month = date.get(Calendar.MONTH)
    val day = date.get(Calendar.DAY_OF_MONTH)

    return dateOf(year, month, day)
}

/**
 * Tworzy obiekt Date na podstawie podanych parametrów.
 * @param year rok
 * @param month miesiąc z przedziału 1-12
 * @param day dzień z przedziału 1-31
 */
fun dateOf(year: Int, month: Int, day: Int): Date {
    return dateOf(year, month, day, 0, 0, 0)
}

/**
 * Tworzy obiekt Date na podstawie podanych parametrów.
 * @param year rok
 * @param month miesiąc z przedziału 1-12
 * @param day dzień z przedziału 1-31
 * @param hours godzina z przedziału 0-23
 * @param minutes minuty z przedziału 0-59
 * @param seconds sekundy z przedziału 0-59
 */
fun dateOf(year: Int, month: Int, day: Int, hours: Int, minutes: Int, seconds: Int): Date {
    return Date(year - 1900, month - 1, day, hours, minutes, seconds)
}

/**
 * Zwraca true, gdy dni roku obu dat są takie same, false, gdy nie
 */
fun Date.isSameDay(date: Date): Boolean {
    val firstCalendar = Calendar.getInstance().apply {
        time = this@isSameDay
    }

    val secondCalendar = Calendar.getInstance().apply {
        time = date
    }

    return ((firstCalendar.get(Calendar.ERA) == secondCalendar.get(Calendar.ERA)) &&
            (firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR)) &&
            (firstCalendar.get(Calendar.DAY_OF_YEAR) == secondCalendar.get(Calendar.DAY_OF_YEAR)))
}

/**
 * Użycie Calendar jest kilka razy szybsze niż SimpleDateFormat.
 */
fun Date.timeFormat(): String {
    val calendar = Calendar.getInstance().apply {
        time = this@timeFormat
    }

    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    return "$hour:$minute"
}

fun Date.dateFormat(): String {
    val calendar = Calendar.getInstance().apply {
        time = this@dateFormat
    }

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return "$year-$month-$day"
}

/**
 * @param field - typ zwracanej wartości, np. Calendar.DAY_OF_MONTH
 */
fun Date.get(field: Int): Int {
    val calendar = Calendar.getInstance().apply {
        time = this@get
    }

    return calendar.get(field)
}