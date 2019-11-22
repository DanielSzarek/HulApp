package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.CityDao
import pl.kamilszustak.hulapp.data.model.City
import retrofit2.Response
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val cityDao: CityDao
) : ResourceRepository<City> {

    override suspend fun insert(item: City) {
        cityDao.insert(item)
    }

    override suspend fun insertAll(items: List<City>) {
        cityDao.insertAll(items)
    }

    override suspend fun update(item: City) {
        cityDao.update(item)
    }

    override suspend fun delete(item: City) {
        cityDao.delete(item)
    }

    override fun getAll(): Flow<Resource<List<City>>> {
        return object : NetworkBoundResource<List<City>>() {
            override fun loadFromDatabase(): Flow<List<City>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override suspend fun fetchFromNetwork(): Response<List<City>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override suspend fun saveFetchResult(data: List<City>) {
                cityDao.insertAll(data)
            }
        }.asFlow()
    }

    override fun getById(id: Long): Flow<Resource<City>> {
        return object : NetworkBoundResource<City>() {
            override fun loadFromDatabase(): Flow<City> {
                return cityDao.getById(id)
            }

            override suspend fun fetchFromNetwork(): Response<City> {
                TODO("not implemented")
            }

            override suspend fun saveFetchResult(data: City) {
                cityDao.insert(data)
            }
        }.asFlow()
    }
}