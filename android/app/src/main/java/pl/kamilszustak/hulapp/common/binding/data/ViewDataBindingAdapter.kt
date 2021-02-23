package pl.kamilszustak.hulapp.common.binding.data

import android.view.View
import androidx.databinding.BindingAdapter
import pl.kamilszustak.hulapp.util.setGone
import pl.kamilszustak.hulapp.util.setVisible

object ViewDataBindingAdapter {
    private const val VISIBLE_IF_NOT_NULL_ATTRIBUTE: String = "visibleIfNotNull"

    @BindingAdapter(VISIBLE_IF_NOT_NULL_ATTRIBUTE)
    @JvmStatic
    fun View.setVisibility(value: Any?) {
        if (value != null) {
            this.setVisible()
        } else {
            this.setGone()
        }
    }
}