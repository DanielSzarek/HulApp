package pl.kamilszustak.hulapp.util

import okhttp3.Request
import pl.kamilszustak.hulapp.common.annotation.Authorization

fun Request.needAuthorization(): Boolean {
    return this.tag(Authorization::class.java) != null
}