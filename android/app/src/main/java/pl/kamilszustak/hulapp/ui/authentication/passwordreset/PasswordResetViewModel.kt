package pl.kamilszustak.hulapp.ui.authentication.passwordreset

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.FormValidator
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.domain.form.Email
import pl.kamilszustak.hulapp.manager.AuthorizationManager
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class PasswordResetViewModel @Inject constructor(
    application: Application,
    private val validator: FormValidator,
    private val authorizationManager: AuthorizationManager
) : StateViewModel(application) {

    val userEmail: FormField<String> = formField {
        +Rule<String>("Nieprawidłowy format") {
            it.isNotBlank() && validator.validate(Email(it))
        }
    }

    val isPasswordResetEnabled: LiveData<Boolean> = FormField.validateFields(userEmail)

    fun onPasswordResetButtonClick() {
        val email = userEmail.data.value

        if (email == null) {
            _error.value = R.string.empty_email_error_message
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            val result = authorizationManager.resetPassword(email)
            result.onSuccess {
                _completed.call()
            }.onFailure { throwable ->
                _error.value = when (throwable) {
                    is NoInternetConnectionException -> R.string.no_internet_connection_error_message
                    else -> R.string.password_reset_error_message
                }
            }

            _isLoading.value = false
        }
    }
}