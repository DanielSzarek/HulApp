package pl.kamilszustak.hulapp.ui.authorization.signup

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.FormValidator
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.form.Email
import pl.kamilszustak.hulapp.data.form.Password
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.common.exception.NoInternetConnectionException
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.*
import timber.log.Timber
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    application: Application,
    private val apiService: ApiService,
    private val validator: FormValidator
) : BaseViewModel(application) {

    val userEmailField: FormField<String> = formField {
        +Rule<String>("Nieprawidłowy format") {
            it.isNotBlank() && validator.validate(Email(it))
        }
    }

    val userPasswordField: FormField<String> = formField {
        +Rule<String>("Hasło musi posiadać min. 8 znaków, 1 cyfrę oraz 1 znak specjalny") {
            it.isNotBlank() && validator.validate(Password(it))
        }
    }

    val retypedUserPasswordField: FormField<String> = formField {
        +Rule<String>("Podane hasła nie sa jednakowe") {
            this.data.value == userPasswordField.data.value
        }
    }

    val userNameField: FormField<String> = formField {
        +Rule<String>("Nie podano imienia") {
            it.isNotBlank()
        }
    }

    val userSurnameField: FormField<String> = formField {
        +Rule<String>("Nie podano nazwiska") {
            it.isNotBlank()
        }
    }

    val userCityField: FormField<City?> = formField {
        isNullable = true
    }

    val userCountryField: FormField<Country?> = formField {
        isNullable = true
    }

    val isSignUpEnabled: LiveData<Boolean> = FormField.validateFields(
        userEmailField,
        userPasswordField,
        retypedUserPasswordField,
        userNameField,
        userSurnameField,
        userCityField,
        userCountryField
    )

    private val _userSignedUp: SingleLiveEvent<Unit> = SingleLiveEvent()
    val userSignedUp: LiveData<Unit> = _userSignedUp

    private val _isSigningUpInProgress: UniqueLiveData<Boolean> = UniqueLiveData()
    val isSigningUpInProgress: LiveData<Boolean> = _isSigningUpInProgress

    private val _signUpError: SingleLiveEvent<String> = SingleLiveEvent()
    val signUpError: LiveData<String> = _signUpError

    fun onCityChoosen(city: City) {
        userCityField.data.setValue(city)
    }

    fun onCountryChoosen(country: Country) {
        userCountryField.data.setValue(country)
    }

    fun onClearCityButtonClick() {
        userCityField.data.setValue(null)
    }

    fun onClearCountryButtonClick() {
        userCountryField.data.setValue(null)
    }

    fun onSignUpButtonClick() {
        val email = userEmailField.data.value
        val password = userPasswordField.data.value
        val retypedPassword = retypedUserPasswordField.data.value
        val name = userNameField.data.value
        val surname = userSurnameField.data.value
        val city = userCityField.data.value
        val country = userCountryField.data.value

        if (!isInternetConnected()) {
            _signUpError.value = "Brak połączenia z Internetem"
        }

        if (email == null ||
            password == null ||
            retypedPassword == null ||
            name == null ||
            surname == null
        ) {
            return
        }

        val user = User(
            email,
            email,
            password,
            name,
            surname,
            city?.id,
            country?.id
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