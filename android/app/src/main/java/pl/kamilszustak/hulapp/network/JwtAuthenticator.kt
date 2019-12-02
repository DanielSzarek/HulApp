package pl.kamilszustak.hulapp.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import pl.kamilszustak.hulapp.data.model.network.ApiServiceHolder
import pl.kamilszustak.hulapp.data.model.network.CreateJwtRequest
import pl.kamilszustak.hulapp.data.model.network.RefreshJwtRequest
import pl.kamilszustak.hulapp.data.repository.JwtTokenRepository
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.util.today
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class JwtAuthenticator @Inject constructor(
    private val apiServiceHolder: ApiServiceHolder,
    private val userDetailsRepository: UserDetailsRepository,
    private val jwtTokenRepository: JwtTokenRepository
) : Authenticator {

    private val responseThreshold: Int = 3
    private var responseCounter: Int = 1
    private var emailHashCode: Int = 0

    init {
        emailHashCode = getEmailHashCode()
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val apiService = apiServiceHolder.service

        val hash = getEmailHashCode()
        if (emailHashCode == hash) {
            responseCounter++
        } else {
            emailHashCode = hash
            responseCounter = 1
        }

        if (responseCounter > responseThreshold || apiService == null) {
            responseCounter = 1
            return null
        }

        val nowTimestamp = Date().time
        val expirationDateTimestamp: Long = JwtTokenRepository.JwtTokenKey.TOKEN_EXPIRATION_DATE_TIMESTAMP.let {
            jwtTokenRepository.getValue(it, it.getDefaultValue())
        }

        val accessToken = if (nowTimestamp > expirationDateTimestamp) {
            val request = getCreateJwtRequest()
            val tokenResponse = apiService.createJwt(request).execute()

            if (!tokenResponse.isSuccessful)
                return null

            val access = tokenResponse.body()?.accessToken
            val refresh = tokenResponse.body()?.refreshToken
            if (access != null && refresh != null) {
                jwtTokenRepository.setValues(
                    JwtTokenRepository.JwtTokenKey.ACCESS_TOKEN to access,
                    JwtTokenRepository.JwtTokenKey.REFRESH_TOKEN to refresh
                )

                jwtTokenRepository.setValue(
                    JwtTokenRepository.JwtTokenKey.TOKEN_EXPIRATION_DATE_TIMESTAMP to
                            (today().time + JWT_ACCESS_TOKEN_EXPIRATION_TIME)
                )
            }

            access
        } else {
            val request = getRefreshJwtRequest()
            val tokenResponse = apiService.refreshJwt(request).execute()

            if (!tokenResponse.isSuccessful)
                return null

            val access = tokenResponse.body()?.accessToken
            if (access != null) {
                jwtTokenRepository.setValue(
                    JwtTokenRepository.JwtTokenKey.ACCESS_TOKEN to access
                )

                jwtTokenRepository.setValue(
                    JwtTokenRepository.JwtTokenKey.TOKEN_EXPIRATION_DATE_TIMESTAMP to
                            (today().time + JWT_ACCESS_TOKEN_EXPIRATION_TIME)
                )
            }

            access
        }

        Timber.i(accessToken)

        return if (accessToken != null) {
            response.request
                .newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        } else {
            null
        }
    }

    private fun getEmailHashCode(): Int {
        val email: String = UserDetailsRepository.UserDetailsKey.USER_EMAIL.let {
            userDetailsRepository.getValue(it, it.getDefaultValue())
        }

        return email.hashCode()
    }

    private fun getCreateJwtRequest(): CreateJwtRequest {
        val email: String = UserDetailsRepository.UserDetailsKey.USER_EMAIL.let {
            userDetailsRepository.getValue(it, it.getDefaultValue())
        }

        val password: String = UserDetailsRepository.UserDetailsKey.USER_PASSWORD.let {
            userDetailsRepository.getValue(it, it.getDefaultValue())
        }

        return CreateJwtRequest(email, password)
    }

    private fun getRefreshJwtRequest(): RefreshJwtRequest {
        val refreshToken: String = JwtTokenRepository.JwtTokenKey.REFRESH_TOKEN.let {
            jwtTokenRepository.getValue(it, it.getDefaultValue())
        }

        return RefreshJwtRequest(refreshToken)
    }

    private fun countResponse(response: Response): Int {
        var res: Response? = response
        var result = 1

        while (res?.priorResponse != null) {
            res = res.priorResponse
            result++
        }

        return result
    }
}