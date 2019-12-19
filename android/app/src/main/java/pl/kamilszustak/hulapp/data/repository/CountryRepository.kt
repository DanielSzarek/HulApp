package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.CountryDao
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val apiService: ApiService
) : ResourceRepository<Country> {

    override fun getAll(): Flow<Resource<List<Country>>> {
        return object : NetworkBoundResource<List<Country>>() {
            override fun loadFromDatabase(): Flow<List<Country>> =
                countryDao.getAll()

            override suspend fun fetchFromNetwork(): Response<List<Country>> =
                apiService.getAllCountries()

            override suspend fun saveFetchResult(data: List<Country>) {
                countryDao.insertAll(data)
            }
        }.asFlow()
    }

    override fun getById(id: Long): Flow<Resource<Country>> {
        return object : NetworkBoundResource<Country>() {
            override fun loadFromDatabase(): Flow<Country> =
                countryDao.getById(id)

            override suspend fun fetchFromNetwork(): Response<Country> =
                apiService.getCountryById(id)

            override suspend fun saveFetchResult(data: Country) {
                countryDao.insert(data)
            }
        }.asFlow()
    }

    fun getByName(name: String): Flow<Resource<List<Country>>> {
        return object : NetworkBoundResource<List<Country>>() {
            override fun loadFromDatabase(): Flow<List<Country>> =
                countryDao.getByName(name)

            override suspend fun fetchFromNetwork(): Response<List<Country>> =
                apiService.getCountriesByName(name)

            override suspend fun saveFetchResult(data: List<Country>) {
                countryDao.insertAll(data)
            }
        }.asFlow()
    }
}