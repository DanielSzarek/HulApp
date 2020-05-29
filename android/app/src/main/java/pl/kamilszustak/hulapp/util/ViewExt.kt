package pl.kamilszustak.hulapp.util

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import com.bumptech.glide.Glide

fun View.animatePressed(duration: Long = 200, endAction: () -> Unit = {}) {
    this.animate()
        .scaleX(0.6F)
        .scaleY(0.6F)
        .setDuration(duration / 2)
        .withEndAction {
            this.animate()
                .scaleX(1F)
                .scaleY(1F)
                .setDuration(duration / 2)
                .withEndAction(endAction)
        }
}

fun View.showKeyboard() {
    requestFocus()
    val inputMethodManager = context.getSystemService<InputMethodManager>()
    inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService<InputMethodManager>()
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun CompoundButton.setTintListByColor(color: Int) {
    buttonTintList = ColorStateList.valueOf(color)
}

fun ImageView.load(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun popupMenu(context: Context, view: View, initialization: PopupMenu.() -> Unit) {
    PopupMenu(context, view).apply {
        this.initialization()
    }.show()
}

fun popupMenu(view: View, initialization: PopupMenu.() -> Unit) {
    popupMenu(view.context, view, initialization)
}