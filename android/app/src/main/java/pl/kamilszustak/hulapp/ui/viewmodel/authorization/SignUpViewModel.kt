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
import pl.kamilszustak.hulapp.data.form.Password
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.util.withMainContext
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class SignUpViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    protected lateinit var apiService: ApiService

    @Inject
    protected lateinit var validator: FormValidator

    val userEmail: UniqueLiveData<String> = UniqueLiveData()

    val userPassword: UniqueLiveData<String> = UniqueLiveData()

    val retypedUserPassword: UniqueLiveData<String> = UniqueLiveData()

    val userName: UniqueLiveData<String> = UniqueLiveData()

    val userSurname: UniqueLiveData<String> = UniqueLiveData()

    val userCity: UniqueLiveData<City> = UniqueLiveData()

    val userCountry: UniqueLiveData<Country> = UniqueLiveData()

    private val _userSignedUp: SingleLiveEvent<Unit> = SingleLiveEvent()
    val userSignedUp: LiveData<Unit> = _userSignedUp

    private val _isSigningUpInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val isSigningUpInProgress: LiveData<Boolean> = _isSigningUpInProgress

    private val _signUpError: SingleLiveEvent<String> = SingleLiveEvent()
    val signUpError: LiveData<String> = _signUpError

    init {
        getApplicationComponent().inject(this)
    }

    fun onCityChoosen(city: City) {
        userCity.setValue(city)
    }

    fun onCountryChoosen(country: Country) {
        userCountry.setValue(country)
    }

    fun signUp() {
        val email = userEmail.value
        val password = userPassword.value
        val retypedPassword = retypedUserPassword.value
        val name = userName.value
        val surname = userSurname.value
        val city = userCity.value
        val country = userCountry.value

        if (!isInternetConnected())
            _signUpError.value = "Brak połączenia z Internetem"

        if (
            email.isNullOrBlank() ||
            password.isNullOrBlank() ||
            retypedPassword.isNullOrBlank() ||
            name.isNullOrBlank() ||
            surname.isNullOrBlank() ||
            city == null ||
            country == null
        ) {
            _signUpError.value = "Wymagane pola nie mogą być puste"
            return
        }

        val userEmail = Email(email)

        if (!validator.validate(userEmail)) {
            _signUpError.value = "Nieprawidłowy format adresu email"
            return
        }

        val userPassword = Password(password)
        if (!validator.validate(userPassword)) {
            _signUpError.value = "Nieprawidłowy format hasła"
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
            city.id,
            country.id
        )

        viewModelScope.launch(Dispatchers.IO) {
            withMainContext {
                _isSigningUpInProgress.setValue(true)
            }

            val response = try {
                apiService.signUp(user)
            } catch (exception: NoInternetConnectionException) {
                exception.printStackTrace()
                _signUpError.value = "Brak połączenia z Internetem"
                return@launch
            }

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