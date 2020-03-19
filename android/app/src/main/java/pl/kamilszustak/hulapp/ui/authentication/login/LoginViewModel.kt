package pl.kamilszustak.hulapp.ui.authentication.login

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
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.common.form.*
import pl.kamilszustak.hulapp.manager.AuthorizationManager
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    application: Application,
    private val settingsRepository: SettingsRepository,
    private val jwtTokenRepository: JwtTokenRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val validator: FormValidator,
    private val authorizationManager: AuthorizationManager
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

    private val _completed: SingleLiveData<Unit> = SingleLiveData()
    val completed: LiveData<Unit> = _completed

    private val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: SingleLiveData<String> = SingleLiveData()
    val error: LiveData<String> = _error

    init {
        val isUserLoggedIn: Boolean = settingsRepository.getValue(SettingsRepository.SettingsKey.IS_USER_LOGGED_IN)

        if (isUserLoggedIn) {
            _completed.call()
        } else {
            clearData()
        }
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
            _error.value = "Brak połączenia z Internetem"

        if (email == null || password == null) {
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            val result = authorizationManager.login(email, password)
            result.onSuccess {
                _completed.callAsync()
            }.onFailure { throwable ->
                _error.value = when (throwable) {
                    is NoInternetConnectionException -> "Brak połączenia z Internetem"
                    else -> "Nie udało się zalogować"
                }
            }

            _isLoading.value = false
        }
    }
}