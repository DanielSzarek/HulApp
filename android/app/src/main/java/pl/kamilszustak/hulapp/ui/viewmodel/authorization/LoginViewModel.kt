package pl.kamilszustak.hulapp.ui.viewmodel.authorization

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.FormValidator
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.form.Email
import pl.kamilszustak.hulapp.data.repository.JwtTokenRepository
import pl.kamilszustak.hulapp.data.repository.SettingsRepository
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.util.withMainContext
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    application: Application,
    private val apiService: ApiService,
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository,
    private val jwtTokenRepository: JwtTokenRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val validator: FormValidator
) : BaseViewModel(application) {

    val userEmail: UniqueLiveData<String> = UniqueLiveData()

    val userPassword: UniqueLiveData<String> = UniqueLiveData()

    private val _loginCompleted: SingleLiveEvent<Unit> = SingleLiveEvent()
    val loginCompleted: LiveData<Unit> = _loginCompleted

    private val _isLoggingInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoggingInProgress: LiveData<Boolean> = _isLoggingInProgress

    private val _loginError: SingleLiveEvent<String> = SingleLiveEvent()
    val loginError: LiveData<String> = _loginError

    init {
        val isUserLoggedIn: Boolean = SettingsRepository.SettingsKey.IS_USER_LOGGED_IN.let {
            settingsRepository.getValue(it, it.getDefaultValue())
        }

        if (isUserLoggedIn)
            _loginCompleted.call()
        else
            clearData()
    }

    private fun clearData() {
        userDetailsRepository.restoreDefaultValues()
        jwtTokenRepository.restoreDefaultValues()
        settingsRepository.restoreDefaultValues()
    }

    fun login() {
        val email = userEmail.value
        val password = userPassword.value

        if (!isInternetConnected())
            _loginError.value = "Brak połączenia z Internetem"

        if (email.isNullOrBlank()) {
            _loginError.value = "Email cannot be blank"
            return
        }

        val userEmail = Email(email)

        if (!validator.validate(userEmail)) {
            _loginError.value = "Nieprawidłowy format adresu email"
            return
        }

        if (password.isNullOrBlank()) {
            _loginError.value = "Password cannot be blank"
            return
        }

        userDetailsRepository.setValues(
            UserDetailsRepository.UserDetailsKey.USER_EMAIL to email,
            UserDetailsRepository.UserDetailsKey.USER_PASSWORD to password
        )

        viewModelScope.launch(Dispatchers.IO) {
            withMainContext {
                _isLoggingInProgress.setValue(true)
            }

            val response = try {
                apiService.login()
            } catch (exception: NoInternetConnectionException) {
                exception.printStackTrace()
                _loginError.value = "Brak połączenia z Internetem"
                return@launch
            }

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    userRepository.insert(body)
                    userDetailsRepository.setValue(
                        UserDetailsRepository.UserDetailsKey.USER_ID to body.id
                    )
                    settingsRepository.setValue(
                        SettingsRepository.SettingsKey.IS_USER_LOGGED_IN to true
                    )
                    withMainContext {
                        _loginCompleted.call()
                    }
                }
            } else {
                Timber.i("Login error")
                withMainContext {
                    _loginError.value = "Nie udało się zalogować"
                }
            }

            withMainContext {
                _isLoggingInProgress.setValue(false)
            }
        }
    }
}