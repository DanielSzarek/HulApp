package pl.kamilszustak.hulapp.common.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import pl.kamilszustak.hulapp.R

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
}