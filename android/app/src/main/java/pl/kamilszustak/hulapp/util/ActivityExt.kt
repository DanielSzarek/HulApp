package pl.kamilszustak.hulapp.util

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner

fun Activity.getAndroidViewModelFactory(): ViewModelProvider.AndroidViewModelFactory {
    return ViewModelProvider.AndroidViewModelFactory(this.application)
}

inline fun Activity.dialog(block: MaterialDialog.() -> Unit): MaterialDialog {
    return MaterialDialog(this).show {
        block()
        cornerRadius(16F)
        if (this@dialog is LifecycleOwner)
            lifecycleOwner(this@dialog)
    }
}

fun Activity.navigateUp(navHostFragmentId: Int): Boolean =
    this.findNavController(navHostFragmentId).navigateUp()