package pl.kamilszustak.hulapp.ui.dialog.country

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.domain.model.Country
import pl.kamilszustak.hulapp.data.repository.CountryRepository
import pl.kamilszustak.hulapp.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class CountryChoiceViewModel @Inject constructor(
    application: Application,
    private val countryRepository: CountryRepository
) : BaseViewModel(application) {

    val countriesResource: ResourceDataSource<List<Country>> = ResourceDataSource()

    val countryName: UniqueLiveData<String> = UniqueLiveData()

    fun loadCountriesByName(name: String) {
        if (name.isBlank()) {
            return
        }

        countriesResource.setFlowSource {
            countryRepository.getByName(name)
        }
    }
}