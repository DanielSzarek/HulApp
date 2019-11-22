package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.data.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
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
        TODO()
    }
}