package pl.kamilszustak.hulapp.ui.main.tracking.details

data class ShareEvent(
    val title: CharSequence,
    val subject: CharSequence = "",
    val chooserTitle: CharSequence = ""
)