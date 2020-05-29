package pl.kamilszustak.hulapp.ui.main.profile.main

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.data.repository.*
import pl.kamilszustak.hulapp.ui.main.profile.BaseProfileViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    application: Application,
    userRepository: UserRepository,
    cityRepository: CityRepository,
    countryRepository: CountryRepository,
    trackRepository: TrackRepository,
    private val settingsRepository: SettingsRepository,
    private val userDetailsRepository: UserDetailsRepository
) : BaseProfileViewModel(
    application,
    userRepository,
    cityRepository,
    countryRepository,
    trackRepository
) {
    private val _logoutEvent: SingleLiveData<Unit> = SingleLiveData()
    val logoutEvent: LiveData<Unit> = _logoutEvent

    private val _openProfilePhoto: SingleLiveData<String> = SingleLiveData()
    val openProfilePhoto: LiveData<String> = _openProfilePhoto

    init {
        userResource.setFlowSource {
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
        userDetailsRepository.restoreDefaultValues()
        _logoutEvent.call()
    }

    fun openProfilePhoto() {
        val profilePhotoUrl = userResource.data.value?.profilePhotoUrl
        if (profilePhotoUrl != null) {
            _openProfilePhoto.value = profilePhotoUrl
        }
    }
}