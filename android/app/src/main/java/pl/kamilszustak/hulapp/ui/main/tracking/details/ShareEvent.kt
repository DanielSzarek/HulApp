package pl.kamilszustak.hulapp.ui.main.tracking.details

import android.content.Intent

data class ShareEvent(
    val intent: Intent,
    val chooserText: String
)