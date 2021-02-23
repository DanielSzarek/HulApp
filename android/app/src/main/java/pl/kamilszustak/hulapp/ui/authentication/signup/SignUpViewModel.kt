package pl.kamilszustak.hulapp.ui.authentication.signup

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.FormValidator
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.domain.form.Email
import pl.kamilszustak.hulapp.domain.form.Password
import pl.kamilszustak.hulapp.domain.model.City
import pl.kamilszustak.hulapp.domain.model.Country
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.manager.AuthorizationManager
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    application: Application,
    private val validator: FormValidator,
    private val authorizationManager: AuthorizationManager
) : StateViewModel(application) {

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
//        +Rule<String>("Podane hasła nie sa jednakowe") {
//            this.data.value == userPasswordField.data.value
//        }
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

    fun onCityChoosen(city: City) {
        userCityField.data.value = city
    }

    fun onCountryChoosen(country: Country) {
        userCountryField.data.value = country
    }

    fun onClearCityButtonClick() {
        userCityField.data.value = null
    }

    fun onClearCountryButtonClick() {
        userCountryField.data.value = null
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
            _errorEvent.value = R.string.no_internet_connection_error_message
            return
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

        performAction(R.string.sign_up_error_message) {
            authorizationManager.signUp(user)
        }
    }
}