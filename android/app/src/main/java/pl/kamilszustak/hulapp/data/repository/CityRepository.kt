package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.CityDao
import pl.kamilszustak.hulapp.domain.model.City
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepository @Inject constructor(
    private val cityDao: CityDao,
    private val apiService: ApiService
) {

    fun getAll(shouldFetch: Boolean = true): Flow<Resource<List<City>>> {
        return object : NetworkBoundResource<List<City>, List<City>>() {
            override fun loadFromDatabase(): Flow<List<City>> =
                cityDao.getAll()

            override fun shouldFetch(data: List<City>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<City>> =
                apiService.getAllCities()

            override suspend fun saveFetchResult(result: List<City>) {
                cityDao.insertAll(result)
            }
        }.asFlow()
    }

    fun getById(id: Long, shouldFetch: Boolean = true): Flow<Resource<City>> {
        return object : NetworkBoundResource<City, City>() {
            override fun loadFromDatabase(): Flow<City> =
                cityDao.getById(id)

            override fun shouldFetch(data: City?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<City> =
                apiService.getCityById(id)

            override suspend fun saveFetchResult(result: City) {
                cityDao.insert(result)
            }
        }.asFlow()
    }

    fun getByName(name: String, shouldFetch: Boolean = true): Flow<Resource<List<City>>> {
        return object : NetworkBoundResource<List<City>, List<City>>() {
            override fun loadFromDatabase(): Flow<List<City>> =
                cityDao.getByNameContaining(name)

            override fun shouldFetch(data: List<City>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<City>> =
                apiService.getCitiesByName(name)

            override suspend fun saveFetchResult(result: List<City>) {
                cityDao.insertAll(result)
            }
        }.asFlow()
    }
}