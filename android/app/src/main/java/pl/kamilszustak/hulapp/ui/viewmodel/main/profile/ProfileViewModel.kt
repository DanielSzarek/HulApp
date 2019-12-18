package pl.kamilszustak.hulapp.ui.viewmodel.main.profile

import android.app.Application
import androidx.lifecycle.*
import pl.kamilszustak.hulapp.common.livedata.ResourceLiveData
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.repository.*
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import pl.kamilszustak.hulapp.util.mapNotNull
import javax.inject.Inject

class ProfileViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    protected lateinit var userRepository: UserRepository

    @Inject
    protected lateinit var userDetailsRepository: UserDetailsRepository

    @Inject
    protected lateinit var settingsRepository: SettingsRepository

    @Inject
    protected lateinit var cityRepository: CityRepository

    @Inject
    protected lateinit var countryRepository: CountryRepository

    val userResource: ResourceLiveData<User> = ResourceLiveData()

    val user: LiveData<User> = userResource.mapNotNull {
        it.data
    }

    private val _logoutEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val logoutEvent: LiveData<Unit> = _logoutEvent

    init {
        getApplicationComponent().inject(this)

        userResource.changeDataSource {
            userRepository.getOne().asLiveData()
        }
    }

    fun onLogoutItemClick() {
        settingsRepository.setValue(
            SettingsRepository.SettingsKey.IS_USER_LOGGED_IN to false
        )
        _logoutEvent.call()
    }
}