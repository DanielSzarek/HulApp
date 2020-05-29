package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.User

@Dao
interface UserDao : BaseDao<User> {
    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: Long): Flow<User>

    @Query("SELECT * FROM users WHERE LOWER(name || ' ' || surname) LIKE '%' || LOWER(:text) || '%'")
    fun getAllByNameOrSurnameContaining(text: String): Flow<List<User>>
}