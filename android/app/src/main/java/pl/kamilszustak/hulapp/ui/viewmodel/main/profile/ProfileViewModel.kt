package pl.kamilszustak.hulapp.ui.viewmodel.main.profile

import android.app.Application
import androidx.lifecycle.*
import pl.kamilszustak.hulapp.common.livedata.ResourceLiveData
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.repository.*
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val settingsRepository: SettingsRepository,
    private val cityRepository: CityRepository,
    private val countryRepository: CountryRepository
) : BaseViewModel(application) {

    val userResource: ResourceLiveData<User> = ResourceLiveData()
    val user: LiveData<User> = userResource.data

    val cityResource: ResourceLiveData<City> = ResourceLiveData()
    val city: LiveData<City> = cityResource.data

    val countryResource: ResourceLiveData<Country> = ResourceLiveData()
    val country: LiveData<Country> = countryResource.data

    private val _logoutEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val logoutEvent: LiveData<Unit> = _logoutEvent

    init {
        userResource.changeDataSource {
            userRepository.getOne().asLiveData()
        }

        cityResource.addSource(user) {
            val cityId = it.cityId
            if (cityId != null) {
                cityResource.changeDataSource {
                    cityRepository.getById(cityId).asLiveData()
                }
            }
        }

        countryResource.addSource(user) {
            val countryId = it.countryId
            if (countryId != null) {
                countryResource.changeDataSource {
                    countryRepository.getById(countryId).asLiveData()
                }
            }
        }
    }

    fun onLogoutItemClick() {
        settingsRepository.setValue(
            SettingsRepository.SettingsKey.IS_USER_LOGGED_IN to false
        )
        _logoutEvent.call()
    }
}