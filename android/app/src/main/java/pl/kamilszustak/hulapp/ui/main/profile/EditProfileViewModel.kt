package pl.kamilszustak.hulapp.ui.main.profile

import android.app.Application
import androidx.lifecycle.asLiveData
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.common.livedata.ResourceLiveData
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    application: Application,
    userRepository: UserRepository
) : BaseViewModel(application) {

    val userResource: ResourceLiveData<User> = ResourceLiveData()

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

    init {
        userResource.changeDataSource {
            userRepository.getOne(false).asLiveData()
        }
    }

    fun onUserLoaded(user: User) {
        userNameField.data.setValue(user.name)
        userSurnameField.data.setValue(user.surname)
    }

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

    fun onSaveButtonClick() {

    }
}