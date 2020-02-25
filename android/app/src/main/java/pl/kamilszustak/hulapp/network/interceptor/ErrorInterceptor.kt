package pl.kamilszustak.hulapp.network.interceptor

import android.app.Application
import okhttp3.Interceptor
import okhttp3.Response
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.util.isInternetConnected
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(
    private val application: Application
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!application.isInternetConnected()) {
            throw NoInternetConnectionException()
        }

        return chain.proceed(chain.request())
    }
}