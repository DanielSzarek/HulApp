package pl.kamilszustak.hulapp.common.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.date.DateFormats
import java.util.*

object DataBindingAdapter {

    @BindingAdapter("app:profilePhoto")
    @JvmStatic
    fun ImageView.setImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.icon_profile)
            .into(this)
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