package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.data.model.User
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService
) : ResourceRepository<User> {

    override suspend fun insert(item: User) {
        userDao.insert(item)
    }

    override suspend fun insertAll(items: List<User>) {
        userDao.insertAll(items)
    }

    override suspend fun update(item: User) {
        userDao.update(item)
    }

    override suspend fun delete(item: User) {
        userDao.delete(item)
    }

    override fun getAll(): Flow<Resource<List<User>>> {
        TODO()
    }

    override fun getById(id: Long): Flow<Resource<User>> {
        TODO()
    }

    fun getOne(): Flow<Resource<User>> {
        return object : NetworkBoundResource<User>() {
            override fun loadFromDatabase(): Flow<User> =
                userDao.getOne()

            override suspend fun fetchFromNetwork(): Response<User> =
                apiService.login()

            override suspend fun saveFetchResult(data: User) {
                userDao.deleteAll()
                userDao.insert(data)
            }
        }.asFlow()
    }
}