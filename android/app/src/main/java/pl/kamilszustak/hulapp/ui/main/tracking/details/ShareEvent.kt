package pl.kamilszustak.hulapp.ui.main.tracking.details

data class ShareEvent(
    val content: CharSequence,
    val subject: CharSequence = "",
    val chooserTitle: CharSequence = ""
)