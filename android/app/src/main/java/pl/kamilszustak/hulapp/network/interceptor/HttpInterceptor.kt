package pl.kamilszustak.hulapp.network.interceptor

import android.app.Application
import okhttp3.Interceptor
import okhttp3.Response
import pl.kamilszustak.hulapp.data.repository.JwtTokenRepository
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.util.containsAny
import pl.kamilszustak.hulapp.util.isInternetConnected
import timber.log.Timber
import javax.inject.Inject

class HttpInterceptor @Inject constructor(
    private val application: Application,
    private val userDetailsRepository: UserDetailsRepository,
    private val jwtTokenRepository: JwtTokenRepository
) : Interceptor {

    private val noAuthorizationUrls: List<String> = listOf(
        "/auth/jwt/create/",
        "/auth/jwt/refresh/",
        "/auth/jwt/verify",
        "/auth/users/reset_password/"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
//        if (!application.isInternetConnected())
//            throw NoInternetConnectionException()

        val originalRequest = chain.request()

        Timber.i("interceptor 1")

        if (originalRequest.url.encodedPath.containsAny(noAuthorizationUrls)) {
            val request = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .method(originalRequest.method, originalRequest.body)
                .build()

            return chain.proceed(request)
        }

        Timber.i("interceptor 2")

        val accessToken: String = JwtTokenRepository.JwtTokenKey.ACCESS_TOKEN.let {
            jwtTokenRepository.getValue(it, it.getDefaultValue())
        }

        Timber.i(accessToken)

        val request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .header("Content-Type", "application/json")
            .method(originalRequest.method, originalRequest.body)
            .build()

        return chain.proceed(request)
    }
}