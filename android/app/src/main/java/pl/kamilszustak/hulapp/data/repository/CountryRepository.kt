package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.CountryDao
import pl.kamilszustak.hulapp.domain.model.Country
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val apiService: ApiService
) {

    fun getAll(shouldFetch: Boolean = true): Flow<Resource<List<Country>>> {
        return object : NetworkBoundResource<List<Country>, List<Country>>() {
            override fun loadFromDatabase(): Flow<List<Country>> =
                countryDao.getAll()

            override fun shouldFetch(data: List<Country>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<Country>> =
                apiService.getAllCountries()

            override suspend fun saveFetchResult(result: List<Country>) {
                countryDao.insertAll(result)
            }
        }.asFlow()
    }

    fun getById(id: Long, shouldFetch: Boolean = true): Flow<Resource<Country>> {
        return object : NetworkBoundResource<Country, Country>() {
            override fun loadFromDatabase(): Flow<Country> =
                countryDao.getById(id)

            override fun shouldFetch(data: Country?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<Country> =
                apiService.getCountryById(id)

            override suspend fun saveFetchResult(result: Country) {
                countryDao.insert(result)
            }
        }.asFlow()
    }

    fun getByName(name: String, shouldFetch: Boolean = true): Flow<Resource<List<Country>>> {
        return object : NetworkBoundResource<List<Country>, List<Country>>() {
            override fun loadFromDatabase(): Flow<List<Country>> =
                countryDao.getByNameContaining(name)

            override fun shouldFetch(data: List<Country>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<Country>> =
                apiService.getCountriesByName(name)

            override suspend fun saveFetchResult(result: List<Country>) {
                countryDao.insertAll(result)
            }
        }.asFlow()
    }
}