package pl.kamilszustak.hulapp.ui.dialog.city

import android.app.Application
import androidx.lifecycle.asLiveData
import pl.kamilszustak.hulapp.common.livedata.ResourceLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.repository.CityRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class CityChoiceViewModel @Inject constructor(
    application: Application,
    private val cityRepository: CityRepository
) : BaseViewModel(application) {

    val citiesResource: ResourceLiveData<List<City>> = ResourceLiveData()

    val cityName: UniqueLiveData<String> = UniqueLiveData()

    fun loadCitiesByName(name: String) {
        citiesResource.changeDataSource {
            cityRepository.getByName(name).asLiveData()
        }
    }
}