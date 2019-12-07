package pl.kamilszustak.hulapp.ui.viewmodel.dialog

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import pl.kamilszustak.hulapp.common.livedata.ResourceLiveData
import pl.kamilszustak.hulapp.common.livedata.SingleLiveEvent
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.repository.CountryRepository
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class CountryChoiceViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    protected lateinit var countryRepository: CountryRepository

    private val countriesResource: ResourceLiveData<List<Country>> = ResourceLiveData()
    val countries: LiveData<List<Country>> = countriesResource.dataLiveData
    val areCountriesLoading: LiveData<Boolean> = countriesResource.loadingLiveData
    val countriesError: SingleLiveEvent<String> = countriesResource.errorLiveData

    val countryName: UniqueLiveData<String> = UniqueLiveData()

    init {
        getApplicationComponent().inject(this)

//        countriesResource.changeDataSource {
//            countryRepository.getAll().asLiveData()
//        }
    }

    fun loadCountriesByName(name: String) {
        countriesResource.changeDataSource {
            countryRepository.getByName(name).asLiveData()
        }
    }
}