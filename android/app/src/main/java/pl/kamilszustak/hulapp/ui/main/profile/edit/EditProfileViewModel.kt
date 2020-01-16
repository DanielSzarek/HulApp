package pl.kamilszustak.hulapp.ui.main.profile.edit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.model.network.UpdateUserRequest
import pl.kamilszustak.hulapp.data.repository.CityRepository
import pl.kamilszustak.hulapp.data.repository.CountryRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.withIoContext
import pl.kamilszustak.hulapp.util.withMainContext
import timber.log.Timber
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val cityRepository: CityRepository,
    private val countryRepository: CountryRepository
) : BaseViewModel(application) {

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

    private val _isSaving: UniqueLiveData<Boolean> = UniqueLiveData()
    val isSaving: LiveData<Boolean> = _isSaving

    private val _saveError: SingleLiveEvent<String> = SingleLiveEvent()
    val saveError: LiveData<String> = _saveError

    private val _saveCompleted: SingleLiveEvent<Unit> = SingleLiveEvent()
    val saveCompleted: LiveData<Unit> = _saveCompleted

    val isSavingEnabled: LiveData<Boolean> = FormField.validateFields(
        userNameField,
        userSurnameField,
        userCityField,
        userCountryField
    )

    init {
        userResource.changeLiveDataSource {
            userRepository.getOne().asLiveData()
        }

        cityResource.result.addSource(userResource.data) {
            val cityId = it.cityId
            if (cityId != null) {
                cityResource.changeFlowSource {
                    cityRepository.getById(cityId)
                }
            } else {
                onCityLoaded(null)
            }
        }

        countryResource.result.addSource(userResource.data) {
            val countryId = it.countryId
            if (countryId != null) {
                countryResource.changeFlowSource {
                    countryRepository.getById(countryId)
                }
            } else {
                onCountryLoaded(null)
            }
        }
    }

    fun onCityLoaded(city: City?) {
        userCityField.data.setValue(city)
    }

    fun onCountryLoaded(country: Country?) {
        userCountryField.data.setValue(country)
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
        if (!isInternetConnected()) {
            _saveError.value = "Brak dostępu do Internetu"
            return
        }

        val name = userNameField.data.value
        val surname = userSurnameField.data.value

        if (name == null || surname == null) {
            _saveError.value = "Imię i nazwisko nie mogą być puste"
            return
        }

        val request = UpdateUserRequest(
            name,
            surname,
            userCityField.data.value?.id,
            userCountryField.data.value?.id
        )

        viewModelScope.launch(Dispatchers.Main) {
            _isSaving.setValue(true)

            val result = withIoContext {
                userRepository.update(request)
            }

            if (result.isSuccess) {
                _saveCompleted.call()
            } else {
                _saveError.value = "Wystąpił błąd podczas edycji profilu"
            }

            _isSaving.setValue(false)
        }
    }
}