package pl.kamilszustak.hulapp.common.binding.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import pl.kamilszustak.hulapp.util.load

object DataBindingAdapter {
    private const val IMAGE_URL_PROPERTY: String = "imageUrl"
    private const val ERROR_TEXT_PROPERTY: String = "errorText"

    @BindingAdapter(IMAGE_URL_PROPERTY)
    @JvmStatic
    fun ImageView.setImage(url: String?) {
        this.load(url)
    }

    @BindingAdapter(ERROR_TEXT_PROPERTY)
    @JvmStatic
    fun TextInputLayout.setErrorMessage(errorMessage: String?) {
        this.error = errorMessage
    }
}