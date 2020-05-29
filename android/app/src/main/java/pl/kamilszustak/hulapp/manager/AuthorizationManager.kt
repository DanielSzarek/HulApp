package pl.kamilszustak.hulapp.manager

import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.data.database.ApplicationDatabase
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.network.ChangePasswordRequestBody
import pl.kamilszustak.hulapp.domain.model.network.PasswordResetRequestBody
import pl.kamilszustak.hulapp.data.repository.SettingsRepository
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.util.withIOContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationManager @Inject constructor(
    private val apiService: ApiService,
    private val userRepository: UserRepository,
    private val userDao: UserDao,
    private val userDetailsRepository: UserDetailsRepository,
    private val settingsRepository: SettingsRepository,
    private val applicationDatabase: ApplicationDatabase
) {
    suspend fun login(email: String, password: String): Result<Unit> {
        userDetailsRepository.setValues(
            UserDetailsRepository.UserDetailsKey.USER_EMAIL to email,
            UserDetailsRepository.UserDetailsKey.USER_PASSWORD to password
        )

        return withIOContext {
            object : NetworkCall<User, Unit>() {
                override suspend fun makeCall(): Response<User> =
                    apiService.login()

                override suspend fun mapResponse(response: User): Unit = Unit

                override suspend fun saveCallResult(result: User) {
                    userDao.insert(result)
                    userDetailsRepository.setValue(
                        UserDetailsRepository.UserDetailsKey.USER_ID to result.id
                    )
                    settingsRepository.setValue(
                        SettingsRepository.SettingsKey.IS_USER_LOGGED_IN to true
                    )
                }
            }.callForResponse()
        }
    }

    suspend fun logout() {
        withIOContext {
            applicationDatabase.clearAllTables()
            userDetailsRepository.restoreDefaultValues()
            settingsRepository.restoreDefaultValues()
        }
    }

    suspend fun signUp(user: User): Result<Unit> {
        return withIOContext {
            object : NetworkCall<User, Unit>() {
                override suspend fun makeCall(): Response<User> =
                    apiService.signUp(user)

                override suspend fun mapResponse(response: User): Unit = Unit
            }.call()
        }
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        return withIOContext {
            object : NetworkCall<Unit, Unit>() {
                override suspend fun makeCall(): Response<Unit> {
                    val request = PasswordResetRequestBody(email)
                    return apiService.resetPassword(request)
                }

                override suspend fun mapResponse(response: Unit): Unit = Unit
            }.call()
        }
    }

    suspend fun changePassword(currentPassword: String, newPassword: String): Result<Unit> {
        return withIOContext {
            object : NetworkCall<ChangePasswordRequestBody, Unit>() {
                override suspend fun makeCall(): Response<ChangePasswordRequestBody> {
                    val request = ChangePasswordRequestBody(currentPassword, newPassword)

                    return apiService.changePassword(request)
                }

                override suspend fun mapResponse(response: ChangePasswordRequestBody): Unit = Unit

                override suspend fun saveCallResult(result: ChangePasswordRequestBody) {
                    userDetailsRepository.setValue(
                        UserDetailsRepository.UserDetailsKey.USER_PASSWORD to result.newPassword
                    )
                }
            }.call()
        }
    }
}