package pl.kamilszustak.hulapp.ui.authentication.login

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.FormValidator
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.data.repository.JwtTokenRepository
import pl.kamilszustak.hulapp.data.repository.SettingsRepository
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.domain.form.Email
import pl.kamilszustak.hulapp.manager.AuthorizationManager
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    application: Application,
    private val settingsRepository: SettingsRepository,
    private val jwtTokenRepository: JwtTokenRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val validator: FormValidator,
    private val authorizationManager: AuthorizationManager
) : StateViewModel(application) {

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

    init {
        val isUserLoggedIn: Boolean = settingsRepository.getValue(SettingsRepository.SettingsKey.IS_USER_LOGGED_IN)

        if (isUserLoggedIn) {
            _actionCompletedEvent.call()
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

        if (!isInternetConnected()) {
            _errorEvent.value = R.string.no_internet_connection_error_message
            return
        }

        if (email == null || password == null) {
            return
        }

        performAction(R.string.authentication_failed_error_message) {
            authorizationManager.login(email, password)
        }
    }
}