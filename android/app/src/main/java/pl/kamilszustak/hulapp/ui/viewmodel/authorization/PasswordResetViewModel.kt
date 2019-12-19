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
import pl.kamilszustak.hulapp.data.model.network.PasswordResetRequest
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class PasswordResetViewModel @Inject constructor(
    application: Application,
    private val apiService: ApiService,
    private val validator: FormValidator
) : BaseViewModel(application) {

    val userEmail: UniqueLiveData<String> = UniqueLiveData()

    private val _resetError: SingleLiveEvent<String> = SingleLiveEvent()
    val resetError: LiveData<String> = _resetError

    private val _resetCompleted: SingleLiveEvent<Unit> = SingleLiveEvent()
    val resetCompleted: LiveData<Unit> = _resetCompleted

    private val _resetInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val resetInProgress: LiveData<Boolean> = _resetInProgress

    fun resetPassword() {
        val email = userEmail.value

        if (!isInternetConnected())
            _resetError.value = "Brak połączenia z Internetem"

        if (email.isNullOrBlank()) {
            _resetError.value = "Nie wpisano adresu email"
            return
        }

        val userEmail = Email(email)

        if (!validator.validate(userEmail)) {
            _resetError.value = "Nieprawidłowy format adresu email"
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            _resetInProgress.setValue(true)

            val request = PasswordResetRequest(email)
            val response = try {
                apiService.resetPassword(request)
            } catch (exception: NoInternetConnectionException) {
                exception.printStackTrace()
                _resetError.value = "Brak połączenia z Internetem"
                return@launch
            }

            if (response.isSuccessful) {
                _resetCompleted.call()
            } else {
                _resetError.value = "Nie udało się zresetować hasła"
            }

            _resetInProgress.setValue(false)
        }
    }
}