package pl.kamilszustak.hulapp.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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

    if (connectivityManager != null && network != null) {
        val connection = connectivityManager.getNetworkCapabilities(network)

        return (connection != null && (
                    connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                )
        )

    }
    return false
}

fun Context.copyToClipboard(label: CharSequence, text: CharSequence): Boolean {
    val clipboardManager = this.getSystemService<ClipboardManager>()
    val clip = ClipData.newPlainText(label, text)
    clipboardManager?.setPrimaryClip(clip)

    return clipboardManager != null
}

