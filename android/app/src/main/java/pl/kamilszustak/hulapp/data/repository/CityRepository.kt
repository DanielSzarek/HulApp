package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.CityDao
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val cityDao: CityDao,
    private val apiService: ApiService
) : ResourceRepository<City> {

    override fun getAll(): Flow<Resource<List<City>>> {
        return object : NetworkBoundResource<List<City>>() {
            override fun loadFromDatabase(): Flow<List<City>> =
                cityDao.getAll()

            override suspend fun fetchFromNetwork(): Response<List<City>> =
                apiService.getAllCities()

            override suspend fun saveFetchResult(data: List<City>) {
                cityDao.insertAll(data)
            }

            override fun shouldFetch(data: List<City>): Boolean = false
        }.asFlow()
    }

    override fun getById(id: Long): Flow<Resource<City>> {
        return object : NetworkBoundResource<City>() {
            override fun loadFromDatabase(): Flow<City> =
                cityDao.getById(id)

            override suspend fun fetchFromNetwork(): Response<City> =
                apiService.getCityById(id)

            override suspend fun saveFetchResult(data: City) {
                cityDao.insert(data)
            }
        }.asFlow()
    }

    fun getByName(name: String): Flow<Resource<List<City>>> {
        return object : NetworkBoundResource<List<City>>() {
            override fun loadFromDatabase(): Flow<List<City>> =
                cityDao.getByName(name)

            override suspend fun fetchFromNetwork(): Response<List<City>> =
                apiService.getCitiesByName(name)

            override suspend fun saveFetchResult(data: List<City>) {
                cityDao.insertAll(data)
            }
        }.asFlow()
    }
}