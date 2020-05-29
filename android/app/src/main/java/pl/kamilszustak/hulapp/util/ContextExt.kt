package pl.kamilszustak.hulapp.util

import android.content.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.core.net.toUri

fun Context.openApplicationSystemSettings() {
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        addCategory(Intent.CATEGORY_DEFAULT)
        data = "package:${this@openApplicationSystemSettings.packageName}".toUri()
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }

    startActivity(intent)
}

inline fun <reified T> Context.getSystemService(): T? {
    return getSystemService(T::class.java)
}

fun Context.isInternetConnected(): Boolean {
    val connectivityManager = this.getSystemService<ConnectivityManager>()
    val network = connectivityManager?.activeNetwork

    if (connectivityManager == null || network == null) {
        return false
    }

    val connection = connectivityManager.getNetworkCapabilities(network)
    val hasCellularTransport = connection?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
    val hasWifiTransport = connection?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false

    return (connection != null && (hasCellularTransport || hasWifiTransport))
}

fun Context.copyToClipboard(label: CharSequence, text: CharSequence): Boolean {
    val clipboardManager = this.getSystemService<ClipboardManager>()
    val clip = ClipData.newPlainText(label, text)
    clipboardManager?.setPrimaryClip(clip)

    return clipboardManager != null
}

fun Context.share(
    text: CharSequence,
    subject: CharSequence = "",
    chooserTitle: CharSequence = ""
): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, chooserTitle)
        if (chooser != null) {
            startActivity(chooser)
            true
        } else {
            false
        }
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}
