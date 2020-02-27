package pl.kamilszustak.hulapp.network

import okhttp3.MultipartBody
import pl.kamilszustak.hulapp.common.annotation.Authorize
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.model.Track
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

    @PATCH("/auth/users/me/")
    @Authorize
    @Multipart
    suspend fun patchUserProfilePhoto(@Part file: MultipartBody.Part): Response<User>

    @PATCH("/auth/users/me/")
    @Authorize
    suspend fun patchUser(@Body updateUserRequest: UpdateUserRequest): Response<User>

    @POST("/auth/users/set_password/")
    @Authorize
    suspend fun changePassword(@Body changePasswordRequestBody: ChangePasswordRequestBody): Response<ChangePasswordRequestBody>

    @POST("/auth/users/reset_password/")
    suspend fun resetPassword(@Body passwordResetRequestBody: PasswordResetRequestBody): Response<Unit>

    @GET("/api/countries/")
    suspend fun getAllCountries(): Response<List<Country>>

    @GET("/api/countries/{id}")
    @Authorize
    suspend fun getCountryById(@Path("id") id: Long): Response<Country>

    @GET("/api/countries/{name}")
    suspend fun getCountriesByName(@Path("name") name: String): Response<List<Country>>

    @GET("/api/cities")
    suspend fun getAllCities(): Response<List<City>>

    @GET("/api/cities/{id}")
    @Authorize
    suspend fun getCityById(@Path("id") id: Long): Response<City>

    @GET("/api/cities/{name}")
    suspend fun getCitiesByName(@Path("name") name: String): Response<List<City>>

    @POST("/api/tracks/")
    @Authorize
    suspend fun postTrack(@Body track: Track): Response<Track>

    @GET("/api/tracks/")
    @Authorize
    suspend fun getAllTracks(): Response<List<Track>>

    @GET("/api/tracks/{id}")
    @Authorize
    suspend fun getTrackById(@Path("id") id: Long): Response<Track>

    @DELETE("/api/tracks/{id}")
    @Authorize
    suspend fun deleteTrackById(@Path("id") id: Long): Response<Unit>

    @GET("/api/users/")
    @Authorize
    suspend fun searchForUsers(@Query("search") text: String): Response<List<User>>

    @GET("/api/users/{id}")
    @Authorize
    suspend fun getUserById(@Path("id") id: Long): Response<User>
}