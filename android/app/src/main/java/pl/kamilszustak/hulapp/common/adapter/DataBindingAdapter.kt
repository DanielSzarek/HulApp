package pl.kamilszustak.hulapp.common.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import pl.kamilszustak.hulapp.R

object DataBindingAdapter {

    @BindingAdapter("src")
    fun ImageView.setImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.icon_profile)
            .into(this)
    }
}