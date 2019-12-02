package pl.kamilszustak.hulapp.network

import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.model.network.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/auth/jwt/create")
    fun createJwt(@Body createJwtRequest: CreateJwtRequest): Call<CreateJwtResponse>

    @POST("/auth/jwt/refresh")
    fun refreshJwt(@Body refreshJwtRequest: RefreshJwtRequest): Call<RefreshJwtResponse>

    @POST("/auth/users/")
    suspend fun signUp(@Body user: User): Response<User>

    @GET("/auth/users/me")
    suspend fun login(): Response<User>

    @POST("/auth/users/reset_password/")
    suspend fun resetPassword(@Body passwordResetRequest: PasswordResetRequest): Response<Unit>
}