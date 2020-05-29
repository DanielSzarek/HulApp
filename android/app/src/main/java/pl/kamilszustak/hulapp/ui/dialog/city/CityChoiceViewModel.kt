package pl.kamilszustak.hulapp.ui.dialog.city

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.domain.model.City
import pl.kamilszustak.hulapp.data.repository.CityRepository
import pl.kamilszustak.hulapp.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class CityChoiceViewModel @Inject constructor(
    application: Application,
    private val cityRepository: CityRepository
) : BaseViewModel(application) {

    val citiesResource: ResourceDataSource<List<City>> = ResourceDataSource()

    val cityName: UniqueLiveData<String> = UniqueLiveData()

    fun loadCitiesByName(name: String) {
        if (name.isBlank()) {
            return
        }

        citiesResource.setFlowSource {
            cityRepository.getByName(name)
        }
    }
}