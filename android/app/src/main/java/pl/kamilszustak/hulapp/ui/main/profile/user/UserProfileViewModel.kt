package pl.kamilszustak.hulapp.ui.main.profile.user

import android.app.Application
import pl.kamilszustak.hulapp.data.repository.CityRepository
import pl.kamilszustak.hulapp.data.repository.CountryRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.main.profile.BaseProfileViewModel
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    application: Application,
    userRepository: UserRepository,
    cityRepository: CityRepository,
    countryRepository: CountryRepository
) : BaseProfileViewModel(
    application,
    userRepository,
    cityRepository,
    countryRepository
) {
    fun loadData(userId: Long, force: Boolean = false) {
        initialize(force) {
            userResource.changeFlowSource {
                userRepository.getById(userId)
            }
        }
    }
}