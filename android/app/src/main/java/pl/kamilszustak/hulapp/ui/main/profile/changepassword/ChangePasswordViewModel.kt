package pl.kamilszustak.hulapp.ui.main.profile.changepassword

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
import pl.kamilszustak.hulapp.domain.form.Password
import pl.kamilszustak.hulapp.manager.AuthorizationManager
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    application: Application,
    private val validator: FormValidator,
    private val authorizationManager: AuthorizationManager
) : StateViewModel(application) {

    val currentPasswordField: FormField<String> = formField {
        +Rule<String>("Hasło musi posiadać min. 8 znaków, 1 cyfrę oraz 1 znak specjalny") {
            it.isNotBlank() && validator.validate(Password(it))
        }
    }

    val newPasswordField: FormField<String> = formField {
        +Rule<String>("Hasło musi posiadać min. 8 znaków, 1 cyfrę oraz 1 znak specjalny") {
            it.isNotBlank() && validator.validate(Password(it))
        }
    }

    val newRetypedPasswordField: FormField<String> = formField {
//        +Rule<String>("Podane hasła nie są jednakowe") {
//            newPasswordField.data.value == this.data.value
//        }
    }

    val isPasswordChangeEnabled: LiveData<Boolean> = FormField.validateFields(
        currentPasswordField,
        newPasswordField,
        newRetypedPasswordField
    )

    fun onChangePasswordButtonClick() {
        val currentPassword = currentPasswordField.data.value
        val newPassword = newPasswordField.data.value

        if (currentPassword == null || newPassword == null) {
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            val result = authorizationManager.changePassword(currentPassword, newPassword)
            result.onSuccess {
                authorizationManager.logout()
                _actionCompletedEvent.call()
            }.onFailure { throwable ->
                _errorEvent.value = when (throwable) {
                    is NoInternetConnectionException -> R.string.no_internet_connection_error_message
                    else -> R.string.password_change_error_message
                }
            }

            _isLoading.value = false
        }
    }
}