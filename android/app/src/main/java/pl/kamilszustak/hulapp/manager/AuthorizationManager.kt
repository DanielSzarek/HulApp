package pl.kamilszustak.hulapp.manager

import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.repository.SettingsRepository
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.util.withIoContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationManager @Inject constructor(
    private val apiService: ApiService,
    private val userRepository: UserRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend fun login(email: String, password: String): Result<Unit> {
        userDetailsRepository.setValues(
            UserDetailsRepository.UserDetailsKey.USER_EMAIL to email,
            UserDetailsRepository.UserDetailsKey.USER_PASSWORD to password
        )

        return withIoContext {
            object : NetworkCall<User, Unit>() {
                override suspend fun makeCall(): Response<User> =
                    apiService.login()

                override suspend fun mapResponse(response: User): Unit = Unit

                override suspend fun saveCallResult(result: User) {
                    userRepository.insert(result)
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
}