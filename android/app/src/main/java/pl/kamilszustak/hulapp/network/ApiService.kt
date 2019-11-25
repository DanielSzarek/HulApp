package pl.kamilszustak.hulapp.network

import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.model.network.PasswordResetRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/auth/users/")
    suspend fun signUp(@Body user: User): Response<User>

    @POST("/auth/users/reset_password/")
    suspend fun resetPassword(@Body passwordResetRequest: PasswordResetRequest): Response<Unit>
}