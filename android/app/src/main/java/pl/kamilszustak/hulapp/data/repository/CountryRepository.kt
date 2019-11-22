package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.CountryDao
import pl.kamilszustak.hulapp.data.model.Country
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val countryDao: CountryDao
) : ResourceRepository<Country> {

    override suspend fun insert(item: Country) {
        countryDao.insert(item)
    }

    override suspend fun insertAll(items: List<Country>) {
        countryDao.insertAll(items)
    }

    override suspend fun update(item: Country) {
        countryDao.update(item)
    }

    override suspend fun delete(item: Country) {
        countryDao.delete(item)
    }

    override fun getAll(): Flow<Resource<List<Country>>> {
        TODO()
    }

    override fun getById(id: Long): Flow<Resource<Country>> {
        TODO()
    }
}