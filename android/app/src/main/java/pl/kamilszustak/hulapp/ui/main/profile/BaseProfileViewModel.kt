package pl.kamilszustak.hulapp.ui.main.profile

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.data.repository.CityRepository
import pl.kamilszustak.hulapp.data.repository.CountryRepository
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.data.repository.UserRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel

abstract class BaseProfileViewModel(
    application: Application,
    protected val userRepository: UserRepository,
    protected val cityRepository: CityRepository,
    protected val countryRepository: CountryRepository,
    protected val trackRepository: TrackRepository
) : BaseViewModel(application) {

    val userResource: ResourceDataSource<User> = ResourceDataSource()
    val cityResource: ResourceDataSource<City> = ResourceDataSource()
    val countryResource: ResourceDataSource<Country> = ResourceDataSource()
    val tracksResource: ResourceDataSource<List<TrackEntity>> = ResourceDataSource()

    init {
        cityResource.result.addSource(userResource.data) { user ->
            user?.cityId?.let { id ->
                cityResource.changeFlowSource {
                    cityRepository.getById(id)
                }
            }
        }

       countryResource.result.addSource(userResource.data) { user ->
            user?.countryId?.let { id ->
                countryResource.changeFlowSource {
                    countryRepository.getById(id)
                }
            }
        }

        tracksResource.result.addSource(userResource.data) { user ->
            tracksResource.changeFlowSource {
                trackRepository.getAllByUserId(user.id, 10)
            }
        }
    }
}