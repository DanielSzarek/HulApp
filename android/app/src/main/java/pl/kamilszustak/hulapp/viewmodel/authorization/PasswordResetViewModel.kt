package pl.kamilszustak.hulapp.viewmodel.authorization

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.network.PasswordResetRequest
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.util.*
import pl.kamilszustak.hulapp.viewmodel.BaseViewModel
import javax.inject.Inject

class PasswordResetViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    protected lateinit var apiService: ApiService

    val userEmail: UniqueLiveData<String> = UniqueLiveData()

    private val _resetError: SingleLiveEvent<String> = SingleLiveEvent()
    val resetError: LiveData<String> = _resetError

    private val _resetCompleted: SingleLiveEvent<Unit> = SingleLiveEvent()
    val resetCompleted: LiveData<Unit> = _resetCompleted

    private val _resetInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val resetInProgress: LiveData<Boolean> = _resetInProgress

    init {
        getApplicationComponent().inject(this)
    }

    fun resetPassword() {
        val email = userEmail.value

        if (!this.isInternetConnected()) {
            _resetError.value = "Brak połączenia z Internetem"
            return
        }

        if (email.isNullOrBlank()) {
            _resetError.value = "Nie wpisano adresu email"
            return
        }

        if (!email.isValidEmail()) {
            _resetError.value = "Nieprawidłowy format adresu email"
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            _resetInProgress.setValue(true)

            val request = PasswordResetRequest(email)
            val response = withIoContext {
                apiService.resetPassword(request)
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