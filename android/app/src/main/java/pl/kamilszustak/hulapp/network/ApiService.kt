package pl.kamilszustak.hulapp.network

import okhttp3.MultipartBody
import pl.kamilszustak.hulapp.common.annotation.Authorize
import pl.kamilszustak.hulapp.domain.model.City
import pl.kamilszustak.hulapp.domain.model.Country
import pl.kamilszustak.hulapp.domain.model.Track
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.comment.CommentJson
import pl.kamilszustak.hulapp.domain.model.network.*
import pl.kamilszustak.hulapp.domain.model.post.PostJson
import pl.kamilszustak.hulapp.domain.model.track.TrackJson
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

    @POST("/api/my-tracks/")
    @Authorize
    suspend fun postTrack(@Body track: Track): Response<TrackJson>

    @GET("/api/my-tracks/")
    @Authorize
    suspend fun getCurrentUserTracks(): Response<List<TrackJson>>

    @GET("/api/track/{id}")
    @Authorize
    suspend fun getTrackById(@Path("id") id: Long): Response<TrackJson>

    @DELETE("/api/track/{id}")
    @Authorize
    suspend fun deleteTrackById(@Path("id") id: Long): Response<Unit>

    @GET("/api/users/")
    @Authorize
    suspend fun searchForUsers(@Query("search") text: String): Response<List<User>>

    @GET("/api/users/{id}")
    @Authorize
    suspend fun getUserById(@Path("id") id: Long): Response<User>

    @GET("/api/tracks/")
    @Authorize
    suspend fun getAllTracksByUserId(@Query("id") userId: Long): Response<List<TrackJson>>

    @GET("/api/post/")
    @Authorize
    suspend fun getAllPosts(): Response<List<PostJson>>

    @GET("/api/post/{id}")
    @Authorize
    suspend fun getPostById(@Path("id") id: Long): Response<PostJson>

    @POST("/api/post/")
    @Authorize
    suspend fun addPost(@Body requestBody: AddPostRequstBody): Response<PostJson>

    @PATCH("/api/post/{id}")
    @Authorize
    suspend fun editPost(
        @Path("id") postId: Long,
        @Body requestBody: EditPostRequestBody
    ): Response<PostJson>

    @DELETE("/api/post/{id}")
    @Authorize
    suspend fun deletePostById(@Path("id") id: Long): Response<Unit>

    @GET("/api/comment/")
    @Authorize
    suspend fun getAllCommentsByPostId(@Query("post") postId: Long): Response<List<CommentJson>>
}