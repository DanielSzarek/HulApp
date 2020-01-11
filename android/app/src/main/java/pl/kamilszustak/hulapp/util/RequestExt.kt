package pl.kamilszustak.hulapp.util

import okhttp3.Request
import pl.kamilszustak.hulapp.common.annotation.Authorize
import retrofit2.Invocation

fun Request.needAuthorization(): Boolean {
    val invocation = this.tag(Invocation::class.java)

    return invocation?.method()?.getAnnotation(Authorize::class.java) != null
}