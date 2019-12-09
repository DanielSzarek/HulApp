package pl.kamilszustak.hulapp.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner

fun Fragment.getAndroidViewModelFactory(): ViewModelProvider.AndroidViewModelFactory {
    return this.requireActivity().getAndroidViewModelFactory()
}

fun Fragment.navigateTo(directions: NavDirections) {
    this.findNavController().navigate(directions)
}

fun Fragment.navigateTo(directions: NavDirections, navOptions: NavOptions) {
    this.findNavController().navigate(directions)
}

fun Fragment.navigateTo(directions: NavDirections, extras: Navigator.Extras) {
    this.findNavController().navigate(directions, extras)
}

fun Fragment.navigateTo(
    destinationId: Int,
    args: Bundle,
    options: NavOptions,
    extras: Navigator.Extras
) {
    this.findNavController().navigate(destinationId, args, options, extras)
}

fun Fragment.navigateUp(): Boolean = this.findNavController().navigateUp()

inline fun Fragment.dialog(crossinline block: MaterialDialog.() -> Unit): MaterialDialog {
    return MaterialDialog(requireContext()).show {
        block()
        cornerRadius(16F)
        lifecycleOwner(this@dialog.viewLifecycleOwner)
    }
}

