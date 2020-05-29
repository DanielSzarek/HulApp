package pl.kamilszustak.hulapp.ui.main.profile.edit

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.data.repository.CityRepository
import pl.kamilszustak.hulapp.data.repository.CountryRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.domain.model.City
import pl.kamilszustak.hulapp.domain.model.Country
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.network.UpdateUserRequest
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val cityRepository: CityRepository,
    private val countryRepository: CountryRepository
) : StateViewModel(application) {

    val userResource: ResourceDataSource<User> = ResourceDataSource()
    val cityResource: ResourceDataSource<City?> = ResourceDataSource()
    val countryResource: ResourceDataSource<Country?> = ResourceDataSource()

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

    val isSavingEnabled: LiveData<Boolean> = FormField.validateFields(
        userNameField,
        userSurnameField,
        userCityField,
        userCountryField
    )

    init {
        userResource.setFlowSource {
            userRepository.getLoggedIn(false)
        }

        cityResource.result.addSource(userResource.data) {
            val cityId = it.cityId
            if (cityId != null) {
                cityResource.setFlowSource {
                    cityRepository.getById(cityId, false)
                }
            } else {
                onCityLoaded(null)
            }
        }

        countryResource.result.addSource(userResource.data) {
            val countryId = it.countryId
            if (countryId != null) {
                countryResource.setFlowSource {
                    countryRepository.getById(countryId, false)
                }
            } else {
                onCountryLoaded(null)
            }
        }
    }

    fun onCityLoaded(city: City?) {
        userCityField.data.value = city
    }

    fun onCountryLoaded(country: Country?) {
        userCountryField.data.value = country
    }

    fun onUserLoaded(user: User) {
        userNameField.data.value = user.name
        userSurnameField.data.value = user.surname
    }

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

    fun onSaveButtonClick() {
        if (!isInternetConnected()) {
            _errorEvent.value = R.string.no_internet_connection_error_message
            return
        }

        val name = userNameField.data.value
        val surname = userSurnameField.data.value

        if (name == null || surname == null) {
            _errorEvent.value = R.string.empty_name_or_surname_error_message
            return
        }

        val request = UpdateUserRequest(
            name,
            surname,
            userCityField.data.value?.id,
            userCountryField.data.value?.id
        )

        performAction(R.string.profile_editing_error_message) {
            withIOContext {
                userRepository.update(request)
            }
        }
    }
}