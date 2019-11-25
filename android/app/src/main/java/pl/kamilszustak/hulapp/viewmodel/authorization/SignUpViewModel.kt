package pl.kamilszustak.hulapp.viewmodel.authorization

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.util.getApplicationComponent
import pl.kamilszustak.hulapp.util.isInternetConnected
import pl.kamilszustak.hulapp.util.isValidEmail
import pl.kamilszustak.hulapp.util.withMainContext
import pl.kamilszustak.hulapp.viewmodel.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class SignUpViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    protected lateinit var apiService: ApiService

    val userEmail: UniqueLiveData<String> = UniqueLiveData()

    val userPassword: UniqueLiveData<String> = UniqueLiveData()

    val retypedUserPassword: UniqueLiveData<String> = UniqueLiveData()

    val userName: UniqueLiveData<String> = UniqueLiveData()

    val userSurname: UniqueLiveData<String> = UniqueLiveData()

    val userCity: UniqueLiveData<String> = UniqueLiveData()

    val userCountry: UniqueLiveData<String> = UniqueLiveData()

    private val _userSignedUp: SingleLiveEvent<Unit> = SingleLiveEvent()
    val userSignedUp: LiveData<Unit> = _userSignedUp

    private val _isSigningUpInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val isSigningUpInProgress: LiveData<Boolean> = _isSigningUpInProgress

    private val _signUpError: SingleLiveEvent<String> = SingleLiveEvent()
    val signUpError: LiveData<String> = _signUpError

    init {
        getApplicationComponent().inject(this)
    }

    fun signUp() {
        val email = userEmail.value
        val password = userPassword.value
        val retypedPassword = retypedUserPassword.value
        val name = userName.value
        val surname = userSurname.value
        val city = userCity.value
        val country = userCountry.value

        if (!this.isInternetConnected()) {
            _signUpError.value = "Brak połączenia z Internetem"
            return
        }

        if (
            email.isNullOrBlank() ||
            password.isNullOrBlank() ||
            retypedPassword.isNullOrBlank() ||
            name.isNullOrBlank() ||
            surname.isNullOrBlank() ||
            city.isNullOrBlank() ||
            country.isNullOrBlank()
        ) {
            _signUpError.value = "Wymagane pola nie mogą być puste"
            return
        }

        if (!email.isValidEmail()) {
            _signUpError.value = "Nieprawidłowy format adresu email"
            return
        }

        if (password != retypedPassword) {
            _signUpError.value = "Hasła nie są jednakowe"
            return
        }

        val user = User(
            email,
            email,
            password,
            name,
            surname,
            city,
            country
        )

        viewModelScope.launch(Dispatchers.IO) {
            withMainContext {
                _isSigningUpInProgress.setValue(true)
            }

            val response = apiService.signUp(user)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    withMainContext {
                        _userSignedUp.call()
                    }
                }
            } else {
                Timber.i("Sign up error")
                withMainContext {
                    _signUpError.value = "Nie udało się utworzyć konta"
                }
            }

            withMainContext {
                _isSigningUpInProgress.setValue(false)
            }
        }
    }
}