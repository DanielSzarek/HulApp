package pl.kamilszustak.hulapp.common.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import pl.kamilszustak.hulapp.R
import java.text.SimpleDateFormat
import java.util.*

object DataBindingAdapter {

    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        Locale.forLanguageTag("PL")
    )

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
            simpleDateFormat.format(date)
        } else {
            ""
        }
    }
}