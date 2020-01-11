package pl.kamilszustak.hulapp.ui.dialog.country

import android.app.Application
import androidx.lifecycle.asLiveData
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.data.repository.CountryRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class CountryChoiceViewModel @Inject constructor(
    application: Application,
    private val countryRepository: CountryRepository
) : BaseViewModel(application) {

    val countriesResource: ResourceDataSource<List<Country>> = ResourceDataSource()

    val countryName: UniqueLiveData<String> = UniqueLiveData()

    fun loadCountriesByName(name: String) {
        countriesResource.changeLiveDataSource {
            countryRepository.getByName(name).asLiveData()
        }
    }
}