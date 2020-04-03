package pl.kamilszustak.hulapp.common.binding.data

import android.widget.TextView
import androidx.databinding.BindingAdapter
import pl.kamilszustak.hulapp.common.date.DateFormats
import java.util.*

object TextViewDataBindingAdapter {
    private const val TEXT_PROPERTY: String = "android:text"

    @JvmStatic
    @BindingAdapter(TEXT_PROPERTY)
    fun TextView.setDate(date: Date?) {
        this.text = if (date != null) {
            DateFormats.dateFormat.format(date)
        } else {
            ""
        }
    }
}