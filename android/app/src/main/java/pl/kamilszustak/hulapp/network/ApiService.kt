package pl.kamilszustak.hulapp.network

import pl.kamilszustak.hulapp.common.annotation.Authorize
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.model.network.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/auth/jwt/create")
    fun createJwt(@Body createJwtRequest: CreateJwtRequest): Call<CreateJwtResponse>

    @POST("/auth/jwt/refresh")
    fun refreshJwt(@Body refreshJwtRequest: RefreshJwtRequest): Call<RefreshJwtResponse>

    @POST("/auth/users/")
    suspend fun signUp(@Body user: User): Response<User>

    @GET("/auth/users/me/")
    @Authorize
    suspend fun login(): Response<User>

    @PUT("/auth/users/me/")
    @Authorize
    suspend fun putUser(@Body user: User): Response<User>

    @POST("/auth/users/set_password/")
    @Authorize
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordRequest>

    @POST("/auth/users/reset_password/")
    suspend fun resetPassword(@Body passwordResetRequest: PasswordResetRequest): Response<Unit>

    @GET("/api/countries/")
    suspend fun getAllCountries(): Response<List<Country>>

    @GET("/api/countries/{id}")
    suspend fun getCountryById(@Path("id") id: Long): Response<Country>

    @GET("/api/countries/{name}")
    suspend fun getCountriesByName(@Path("name") name: String): Response<List<Country>>

    @GET("/api/cities")
    suspend fun getAllCities(): Response<List<City>>

    @GET("/api/cities/{id}")
    suspend fun getCityById(@Path("id") id: Long): Response<City>

    @GET("/api/cities/{name}")
    suspend fun getCitiesByName(@Path("name") name: String): Response<List<City>>
}