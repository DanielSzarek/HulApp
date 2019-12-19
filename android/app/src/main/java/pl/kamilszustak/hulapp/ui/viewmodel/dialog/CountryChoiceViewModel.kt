package pl.kamilszustak.hulapp.ui.viewmodel.dialog

import android.app.Application
import androidx.lifecycle.asLiveData
import pl.kamilszustak.hulapp.common.livedata.ResourceLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.repository.CountryRepository
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class CountryChoiceViewModel @Inject constructor(
    application: Application,
    private val countryRepository: CountryRepository
) : BaseViewModel(application) {

    val countriesResource: ResourceLiveData<List<Country>> = ResourceLiveData()

    val countryName: UniqueLiveData<String> = UniqueLiveData()

    fun loadCountriesByName(name: String) {
        countriesResource.changeDataSource {
            countryRepository.getByName(name).asLiveData()
        }
    }
}