package pl.kamilszustak.hulapp.ui.main.profile.main

import android.app.Application
import androidx.lifecycle.*
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.data.repository.*
import pl.kamilszustak.hulapp.ui.main.profile.BaseProfileViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    application: Application,
    userRepository: UserRepository,
    cityRepository: CityRepository,
    countryRepository: CountryRepository,
    private val settingsRepository: SettingsRepository
) : BaseProfileViewModel(
    application,
    userRepository,
    cityRepository,
    countryRepository
) {
    private val _logoutEvent: SingleLiveData<Unit> = SingleLiveData()
    val logoutEvent: LiveData<Unit> = _logoutEvent

    private val _openProfilePhoto: SingleLiveData<String> = SingleLiveData()
    val openProfilePhoto: LiveData<String> = _openProfilePhoto

    init {
        userResource.changeFlowSource {
            userRepository.getLoggedIn()
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