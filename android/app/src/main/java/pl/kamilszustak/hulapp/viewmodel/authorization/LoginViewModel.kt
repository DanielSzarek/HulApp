package pl.kamilszustak.hulapp.viewmodel.authorization

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.repository.SettingsRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.util.getApplicationComponent
import pl.kamilszustak.hulapp.util.isInternetConnected
import pl.kamilszustak.hulapp.util.isValidEmail
import pl.kamilszustak.hulapp.viewmodel.BaseViewModel
import javax.inject.Inject

class LoginViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    protected lateinit var apiService: ApiService

    @Inject
    protected lateinit var settingsRepository: SettingsRepository

    @Inject
    protected lateinit var userRepository: UserRepository

    val userEmail: UniqueLiveData<String> = UniqueLiveData()

    val userPassword: UniqueLiveData<String> = UniqueLiveData()

    private val _userLoggedIn: SingleLiveEvent<Unit> = SingleLiveEvent()
    val userLoggedIn: LiveData<Unit> = _userLoggedIn

    private val _isLoggingInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoggingInProgress: LiveData<Boolean> = _isLoggingInProgress

    private val _loginError: SingleLiveEvent<String> = SingleLiveEvent()
    val loginError: LiveData<String> = _loginError

    init {
        getApplicationComponent().inject(this)

        val isUserLoggedIn = SettingsRepository.SettingsKey.IS_USER_LOGGED_IN.let {
            settingsRepository.getValue<Boolean>(it, it.getDefaultValue())
        }

        if (isUserLoggedIn)
            _userLoggedIn.call()
    }

    fun login() {
        val email = userEmail.value
        val password = userPassword.value

        if (!this.isInternetConnected()) {
            _loginError.value = "Brak połączenia z Internetem"
            return
        }

        if (email.isNullOrBlank()) {
            _loginError.value = "Email cannot be blank"
            return
        }

        if (!email.isValidEmail()) {
            _loginError.value = "Nieprawidłowy format adresu email"
            return
        }

        if (password.isNullOrBlank()) {
            _loginError.value = "Password cannot be blank"
            return
        }
    }
}