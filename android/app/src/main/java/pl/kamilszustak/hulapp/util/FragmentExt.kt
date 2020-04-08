package pl.kamilszustak.hulapp.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner

fun Fragment.navigateTo(directions: NavDirections) {
    val navController = this.findNavController()
    val isSafe = isNavigationSafe(navController, directions)
    if (isSafe) {
        navController.navigate(directions)
    }
}

fun Fragment.navigateTo(directions: NavDirections, navOptions: NavOptions) {
    val navController = this.findNavController()
    val isSafe = isNavigationSafe(navController, directions)
    if (isSafe) {
        navController.navigate(directions, navOptions)
    }
}

fun Fragment.navigateTo(directions: NavDirections, extras: Navigator.Extras) {
    val navController = this.findNavController()
    val isSafe = isNavigationSafe(navController, directions)
    if (isSafe) {
        navController.navigate(directions, extras)
    }
}

fun Fragment.navigateTo(
    destinationId: Int,
    args: Bundle,
    options: NavOptions,
    extras: Navigator.Extras
) {
    val navController = findNavController()
    val isSafe = isNavigationSafe(navController, destinationId)
    if (isSafe) {
        navController.navigate(destinationId, args, options, extras)
    }
}

private fun isNavigationSafe(navController: NavController, directions: NavDirections): Boolean =
    isNavigationSafe(navController, directions.actionId)

private fun isNavigationSafe(navController: NavController, actionId: Int): Boolean =
    navController.currentDestination?.getAction(actionId) != null

fun Fragment.navigateUp(): Boolean = this.findNavController().navigateUp()

inline fun Fragment.dialog(crossinline block: MaterialDialog.() -> Unit): MaterialDialog {
    return MaterialDialog(requireContext()).show {
        block()
        cornerRadius(16F)
        lifecycleOwner(this@dialog.viewLifecycleOwner)
    }
}

fun Fragment.copyToClipboard(label: CharSequence, text: CharSequence): Boolean =
    this.context?.copyToClipboard(label, text) ?: false

fun Fragment.share(
    text: CharSequence,
    subject: CharSequence = "",
    chooserTitle: CharSequence = ""
): Boolean = this.activity?.share(text, subject, chooserTitle) ?: false