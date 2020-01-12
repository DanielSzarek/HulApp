package pl.kamilszustak.hulapp.ui.main.profile.changepassword

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.FormValidator
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.form.Password
import pl.kamilszustak.hulapp.data.repository.SettingsRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.withMainContext
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository,
    private val validator: FormValidator
) : BaseViewModel(application) {

    val currentPasswordField: FormField<String> = formField {
        +Rule<String>("Nieprawidłowy format hasła") {
            it.isNotBlank() //&& validator.validate(Password(it))
        }
    }

    val newPasswordField: FormField<String> = formField {
        +Rule<String>("Nieprawidłowy format hasła") {
            it.isNotBlank() && validator.validate(Password(it))
        }
    }

    val newRetypedPasswordField: FormField<String> = formField {
        +Rule<String>("Podane hasła nie są jednakowe") {
            newPasswordField.data.value == this.data.value
        }
    }

    val isPasswordChangeEnabled = FormField.validateFields(
        currentPasswordField,
        newPasswordField,
        newRetypedPasswordField
    )

    private val _passwordChangeCompleted: SingleLiveEvent<Unit> = SingleLiveEvent()
    val passwordChangeCompleted: LiveData<Unit> = _passwordChangeCompleted

    private val _isPasswordChanging: UniqueLiveData<Boolean> = UniqueLiveData()
    val isPasswordChanging: LiveData<Boolean> = _isPasswordChanging

    private val _passwordChangeError: SingleLiveEvent<String> = SingleLiveEvent()
    val passwordChangeError: LiveData<String> = _passwordChangeError

    fun onChangePasswordButtonClick() {
        _isPasswordChanging.setValue(true)

        val currentPassword = currentPasswordField.data.value
        val newPassword = newPasswordField.data.value

        if (currentPassword == null || newPassword == null) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.changePassword(currentPassword, newPassword)

            if (result.isSuccess) {
                logoutUser()
                withMainContext {
                    _passwordChangeCompleted.call()
                }
            } else {
                withMainContext {
                    _passwordChangeError.value = "Wystąpił błąd podczas zmiany hasła"
                }
            }

            withMainContext {
                _isPasswordChanging.setValue(false)
            }
        }
    }

    private fun logoutUser() {
        settingsRepository.setValue(
            SettingsRepository.SettingsKey.IS_USER_LOGGED_IN to false
        )
    }
}