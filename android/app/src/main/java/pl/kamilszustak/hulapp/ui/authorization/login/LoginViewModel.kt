package pl.kamilszustak.hulapp.ui.authorization.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.form.Email
import pl.kamilszustak.hulapp.data.repository.JwtTokenRepository
import pl.kamilszustak.hulapp.data.repository.SettingsRepository
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.common.form.*
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
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

    val userEmailField: FormField<String> = formField {
        +Rule<String>("Nieprawidłowy format") {
            it.isNotBlank() && validator.validate(Email(it))
        }
    }

    val userPasswordField: FormField<String> = formField {
        +Rule<String>("Hasło musi posiadać min. 8 znaków, 1 cyfrę oraz 1 znak specjalny") {
            it.isNotBlank() //&& validator.validate(Password(it))
        }
    }

    val isLoginEnabled: LiveData<Boolean> = FormField.validateFields(
        userEmailField,
        userPasswordField
    )

    private val _loginCompleted: SingleLiveData<Unit> = SingleLiveData()
    val loginCompleted: LiveData<Unit> = _loginCompleted

    private val _isLoggingInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoggingInProgress: LiveData<Boolean> = _isLoggingInProgress

    private val _loginError: SingleLiveData<String> = SingleLiveData()
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
        val email = userEmailField.data.value
        val password = userPasswordField.data.value

        if (!isInternetConnected())
            _loginError.value = "Brak połączenia z Internetem"

        if (email == null || password == null) {
            return
        }

        userDetailsRepository.setValues(
            UserDetailsRepository.UserDetailsKey.USER_EMAIL to email,
            UserDetailsRepository.UserDetailsKey.USER_PASSWORD to password
        )

        viewModelScope.launch(Dispatchers.IO) {
            _isLoggingInProgress.postValue(true)

            val response = try {
                apiService.login()
            } catch (exception: NoInternetConnectionException) {
                exception.printStackTrace()
                _loginError.postValue("Brak połączenia z Internetem")
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
                    _loginCompleted.callAsync()
                }
            } else {
                _loginError.postValue("Nie udało się zalogować")
            }

            _isLoggingInProgress.postValue(false)
        }
    }
}