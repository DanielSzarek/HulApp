package pl.kamilszustak.hulapp.ui.main.profile.main

import android.app.Application
import androidx.lifecycle.*
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.repository.*
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository,
    private val cityRepository: CityRepository,
    private val countryRepository: CountryRepository
) : BaseViewModel(application) {

    val userResource: ResourceDataSource<User> = ResourceDataSource()

    val cityResource: ResourceDataSource<City> = ResourceDataSource()

    val countryResource: ResourceDataSource<Country> = ResourceDataSource()

    private val _logoutEvent: SingleLiveData<Unit> = SingleLiveData()
    val logoutEvent: LiveData<Unit> = _logoutEvent

    private val _openProfilePhoto: SingleLiveData<String> = SingleLiveData()
    val openProfilePhoto: LiveData<String> = _openProfilePhoto

    init {
        userResource.changeFlowSource {
            userRepository.getLoggedIn()
        }

        cityResource.result.addSource(userResource.data) {
            val cityId = it.cityId
            if (cityId != null) {
                cityResource.changeFlowSource {
                    cityRepository.getById(cityId)
                }
            }
        }

        countryResource.result.addSource(userResource.data) {
            val countryId = it.countryId
            if (countryId != null) {
                countryResource.changeFlowSource {
                    countryRepository.getById(countryId)
                }
            }
        }
    }

    fun onRefresh() {
        userResource.refresh()
    }

    fun onLogoutItemClick() {
        settingsRepository.setValue(
            SettingsRepository.SettingsKey.IS_USER_LOGGED_IN to false
        )
        _logoutEvent.call()
    }

    fun openProfilePhoto() {
        val profilePhotoUrl = userResource.data.value?.profilePhotoUrl
        if (profilePhotoUrl != null) {
            _openProfilePhoto.value = profilePhotoUrl
        }
    }
}