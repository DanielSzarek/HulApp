package pl.kamilszustak.hulapp.ui.viewmodel.dialog

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import pl.kamilszustak.hulapp.common.livedata.ResourceLiveData
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.data.repository.CityRepository
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class CityChoiceViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    protected lateinit var cityRepository: CityRepository

    private val citiesResource: ResourceLiveData<List<City>> = ResourceLiveData()
    val cities: LiveData<List<City>> = citiesResource.dataLiveData
    val areCitiesLoading: LiveData<Boolean> = citiesResource.loadingLiveData
    val citiesError: SingleLiveEvent<String> = citiesResource.errorLiveData

    val cityName: UniqueLiveData<String> = UniqueLiveData()

    init {
        getApplicationComponent().inject(this)
    }

    fun loadCitiesByName(name: String) {
        citiesResource.changeDataSource {
            cityRepository.getByName(name).asLiveData()
        }
    }
}