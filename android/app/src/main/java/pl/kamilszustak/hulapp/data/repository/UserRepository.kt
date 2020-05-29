package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.network.UpdateUserRequest
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val userDetailsRepository: UserDetailsRepository
) {
    suspend fun uploadProfilePhoto(file: File): Result<Unit> {
        return object : NetworkCall<User, Unit>() {
            override suspend fun makeCall(): Response<User> {
                val body = MultipartBody.Part.createFormData(
                    "profile_img",
                    file.name,
                    file.asRequestBody()
                )

                return apiService.patchUserProfilePhoto(body)
            }

            override suspend fun saveCallResult(result: User) {
                userDao.insert(result)
            }

            override suspend fun mapResponse(response: User): Unit = Unit
        }.callForResponse()
    }

    suspend fun update(updateUserRequest: UpdateUserRequest): Result<Unit> {
        return object : NetworkCall<User, Unit>() {
            override suspend fun makeCall(): Response<User> =
                apiService.patchUser(updateUserRequest)

            override suspend fun saveCallResult(result: User) {
                userDao.update(result)
            }

            override suspend fun mapResponse(response: User): Unit = Unit
        }.callForResponse()
    }

    fun getLoggedIn(shouldFetch: Boolean = true): Flow<Resource<User>> {
        val userId = userDetailsRepository.getValue<Long>(UserDetailsRepository.UserDetailsKey.USER_ID)

        return object : NetworkBoundResource<User, User>() {
            override fun loadFromDatabase(): Flow<User> =
                userDao.getById(userId)

            override fun shouldFetch(data: User?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<User> =
                apiService.login()

            override suspend fun saveFetchResult(result: User) {
                userDao.insert(result)
                userDetailsRepository.setValue(
                    UserDetailsRepository.UserDetailsKey.USER_ID to result.id
                )
            }
        }.asFlow()
    }

    fun searchFor(text: String, shouldFetch: Boolean = true): Flow<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, List<User>>() {
            override fun loadFromDatabase(): Flow<List<User>> =
                userDao.getAllByNameOrSurnameContaining(text)

            override fun shouldFetch(data: List<User>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<User>> =
                apiService.searchForUsers(text)

            override suspend fun saveFetchResult(result: List<User>) {
                userDao.insertAll(result)
            }
        }.asFlow()
    }

    fun getById(id: Long, shouldFetch: Boolean = true): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, User>() {
            override fun loadFromDatabase(): Flow<User> =
                userDao.getById(id)

            override fun shouldFetch(data: User?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<User> =
                apiService.getUserById(id)

            override suspend fun saveFetchResult(result: User) {
                userDao.insert(result)
            }
        }.asFlow()
    }
}