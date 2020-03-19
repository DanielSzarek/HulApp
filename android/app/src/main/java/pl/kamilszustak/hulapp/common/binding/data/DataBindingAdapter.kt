package pl.kamilszustak.hulapp.common.binding.data

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import pl.kamilszustak.hulapp.common.date.DateFormats
import pl.kamilszustak.hulapp.util.load
import java.util.*

object DataBindingAdapter {

    @BindingAdapter("app:profilePhoto")
    @JvmStatic
    fun ImageView.setImage(url: String?) {
        this.load(url)
    }

    @BindingAdapter("app:errorText")
    @JvmStatic
    fun TextInputLayout.setErrorMessage(errorMessage: String?) {
        this.error = errorMessage
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun TextView.setDate(date: Date?) {
        this.text = if (date != null) {
            DateFormats.dateFormat.format(date)
        } else {
            ""
        }
    }
}