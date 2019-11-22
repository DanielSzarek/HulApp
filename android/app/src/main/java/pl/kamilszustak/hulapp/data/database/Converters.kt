package pl.kamilszustak.hulapp.data.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        value?.let {
            return Date(it)
        }
        return null
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}