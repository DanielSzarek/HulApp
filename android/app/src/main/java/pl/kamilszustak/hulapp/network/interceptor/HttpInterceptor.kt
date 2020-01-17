package pl.kamilszustak.hulapp.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import pl.kamilszustak.hulapp.common.annotation.Authorize
import pl.kamilszustak.hulapp.data.repository.JwtTokenRepository
import pl.kamilszustak.hulapp.util.needAuthorization
import retrofit2.Invocation
import timber.log.Timber
import javax.inject.Inject

class HttpInterceptor @Inject constructor(
    private val jwtTokenRepository: JwtTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (!originalRequest.needAuthorization()) {
            val request = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .method(originalRequest.method, originalRequest.body)
                .build()

            return chain.proceed(request)
        }

        val accessToken: String = JwtTokenRepository.JwtTokenKey.ACCESS_TOKEN.let {
            jwtTokenRepository.getValue(it, it.getDefaultValue())
        }

        val request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .header("Content-Type", "application/json")
            .method(originalRequest.method, originalRequest.body)
            .build()

        return chain.proceed(request)
    }
}