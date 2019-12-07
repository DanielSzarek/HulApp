package pl.kamilszustak.hulapp.util

import okhttp3.Request
import pl.kamilszustak.hulapp.common.annotation.Authorize

fun Request.needAuthorization(): Boolean {
    return this.tag(Authorize::class.java) != null
}