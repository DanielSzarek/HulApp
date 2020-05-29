package pl.kamilszustak.hulapp.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import pl.kamilszustak.hulapp.domain.model.network.ApiServiceHolder
import pl.kamilszustak.hulapp.domain.model.network.CreateJwtRequest
import pl.kamilszustak.hulapp.domain.model.network.RefreshJwtRequest
import pl.kamilszustak.hulapp.data.repository.JwtTokenRepository
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.util.today
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
        val expirationDateTimestamp: Long = jwtTokenRepository.getValue(JwtTokenRepository.JwtTokenKey.TOKEN_EXPIRATION_DATE_TIMESTAMP)

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
        val email: String = userDetailsRepository.getValue(UserDetailsRepository.UserDetailsKey.USER_EMAIL)

        return email.hashCode()
    }

    private fun getCreateJwtRequest(): CreateJwtRequest {
        val email: String = userDetailsRepository.getValue(UserDetailsRepository.UserDetailsKey.USER_EMAIL)
        val password: String = userDetailsRepository.getValue(UserDetailsRepository.UserDetailsKey.USER_PASSWORD)

        return CreateJwtRequest(email, password)
    }

    private fun getRefreshJwtRequest(): RefreshJwtRequest {
        val refreshToken: String = jwtTokenRepository.getValue(JwtTokenRepository.JwtTokenKey.REFRESH_TOKEN)

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