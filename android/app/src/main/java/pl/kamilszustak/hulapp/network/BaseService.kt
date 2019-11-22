package pl.kamilszustak.hulapp.network

import pl.kamilszustak.hulapp.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BaseService {

    @POST("/auth/users/")
    suspend fun signUp(@Body user: User): Response<User>
}