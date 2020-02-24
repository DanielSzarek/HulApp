package pl.kamilszustak.hulapp.ui.authorization.passwordreset

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.form.FormValidator
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.form.Email
import pl.kamilszustak.hulapp.data.model.network.PasswordResetRequestBody
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.manager.AuthorizationManager
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.withIoContext
import javax.inject.Inject

class PasswordResetViewModel @Inject constructor(
    application: Application,
    private val apiService: ApiService,
    private val validator: FormValidator,
    private val authorizationManager: AuthorizationManager
) : BaseViewModel(application) {

    val userEmail: FormField<String> = formField {
        +Rule<String>("Nieprawidłowy format") {
            it.isNotBlank() && validator.validate(Email(it))
        }
    }

    val isPasswordResetEnabled: LiveData<Boolean> = FormField.validateFields(userEmail)

    private val _resetError: SingleLiveData<String> = SingleLiveData()
    val resetError: LiveData<String> = _resetError

    private val _resetCompleted: SingleLiveData<Unit> = SingleLiveData()
    val resetCompleted: LiveData<Unit> = _resetCompleted

    private val _resetInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val resetInProgress: LiveData<Boolean> = _resetInProgress

    fun onPasswordResetButtonClick() {
        val email = userEmail.data.value

        if (!isInternetConnected())
            _resetError.value = "Brak połączenia z Internetem"

        if (email == null) {
            _resetError.value = "Nie wpisano adresu email"
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            _resetInProgress.value = true

            val result = withIoContext {
                authorizationManager.resetPassword(email)
            }

            result.onSuccess {
                _resetCompleted.call()
            }.onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is NoInternetConnectionException -> "Brak połączenia z Internetem"
                    else -> "Nie udało się zresetować hasła"
                }
                _resetError.value = errorMessage
            }

            _resetInProgress.value = false
        }
    }
}