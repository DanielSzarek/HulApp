package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType
import okhttp3.MultipartBody
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.model.network.ChangePasswordRequest
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val userDetailsRepository: UserDetailsRepository
) {

    suspend fun insert(item: User) {
        userDao.insert(item)
    }

    suspend fun uploadProfilePhoto(file: File): Result<Unit> {
        return object : NetworkCall<User, Unit>() {
            override suspend fun makeCall(): Response<User> {
                val body = MultipartBody.Part.createFormData(
                    "profile_img",
                    file.name,
                    file.asRequestBody()
                )

                return apiService.putProfilePhoto(body)
            }

            override suspend fun saveCallResult(result: User) {
                userDao.insert(result)
            }

            override suspend fun mapResponse(response: User): Unit = Unit
        }.call()
    }

    suspend fun update(user: User): Result<Unit> {
        return object : NetworkCall<User, Unit>() {
            override suspend fun makeCall(): Response<User> =
                apiService.putUser(user)

            override suspend fun saveCallResult(result: User) {
                userDao.update(result)
            }

            override suspend fun mapResponse(response: User): Unit = Unit
        }.call()
    }

    fun getOne(shouldFetch: Boolean = true): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, User>() {
            override fun loadFromDatabase(): Flow<User> =
                userDao.getOne()

            override fun shouldFetch(data: User?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<User> =
                apiService.login()

            override suspend fun saveFetchResult(result: User) {
                userDao.deleteAll()
                userDao.insert(result)
            }
        }.asFlow()
    }

    suspend fun changePassword(currentPassword: String, newPassword: String): Result<Unit> {
        return object : NetworkCall<ChangePasswordRequest, Unit>() {
            override suspend fun makeCall(): Response<ChangePasswordRequest> {
                val request = ChangePasswordRequest(currentPassword, newPassword)

                return apiService.changePassword(request)
            }

            override suspend fun saveCallResult(result: ChangePasswordRequest) {
                userDetailsRepository.setValue(
                    UserDetailsRepository.UserDetailsKey.USER_PASSWORD to result.newPassword
                )
            }

            override suspend fun mapResponse(response: ChangePasswordRequest): Unit = Unit
        }.call()
    }
}