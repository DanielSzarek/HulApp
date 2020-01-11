package pl.kamilszustak.hulapp.common.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout

object DataBindingAdapter {

    @BindingAdapter("src")
    @JvmStatic
    fun ImageView.setImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }

    @BindingAdapter("app:errorText")
    @JvmStatic
    fun TextInputLayout.setErrorMessage(errorMessage: String?) {
        this.error = errorMessage
    }
}