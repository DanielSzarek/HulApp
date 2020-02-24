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
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.form.Password
import pl.kamilszustak.hulapp.data.repository.SettingsRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository,
    private val validator: FormValidator
) : BaseViewModel(application) {

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
        +Rule<String>("Podane hasła nie są jednakowe") {
            newPasswordField.data.value == this.data.value
        }
    }

    val isPasswordChangeEnabled: LiveData<Boolean> = FormField.validateFields(
        currentPasswordField,
        newPasswordField,
        newRetypedPasswordField
    )

    private val _passwordChangeCompleted: SingleLiveData<Unit> = SingleLiveData()
    val passwordChangeCompleted: LiveData<Unit> = _passwordChangeCompleted

    private val _isPasswordChanging: UniqueLiveData<Boolean> = UniqueLiveData()
    val isPasswordChanging: LiveData<Boolean> = _isPasswordChanging

    private val _passwordChangeError: SingleLiveData<String> = SingleLiveData()
    val passwordChangeError: LiveData<String> = _passwordChangeError

    fun onChangePasswordButtonClick() {
        val currentPassword = currentPasswordField.data.value
        val newPassword = newPasswordField.data.value

        if (currentPassword == null || newPassword == null) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _isPasswordChanging.postValue(true)

            val result = userRepository.changePassword(currentPassword, newPassword)
            result.onSuccess {
                logoutUser()
                _passwordChangeCompleted.callAsync()
            }.onFailure {
                _passwordChangeError.postValue("Wystąpił błąd podczas zmiany hasła")
            }

            _isPasswordChanging.postValue(false)
        }
    }


    private fun logoutUser() {
        settingsRepository.setValue(
            SettingsRepository.SettingsKey.IS_USER_LOGGED_IN to false
        )
    }
}