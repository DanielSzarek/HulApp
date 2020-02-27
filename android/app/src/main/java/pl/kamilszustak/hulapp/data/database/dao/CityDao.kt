package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.data.model.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: Collection<City>): List<Long>

    @Update
    suspend fun update(city: City)

    @Delete
    suspend fun delete(city: City)

    @Query("SELECT * FROM cities")
    fun getAll(): Flow<List<City>>

    @Query("SELECT * FROM cities WHERE id = :id")
    fun getById(id: Long): Flow<City>

    @Query(
        "SELECT * FROM cities WHERE LOWER(name) LIKE LOWER(:text) || '%'" +
                "OR LOWER(name) LIKE '% ' || LOWER(:text) || '%'" +
                "OR LOWER(name) LIKE '%-' || LOWER(:text) || '%'"
    )
    fun getByNameContaining(text: String): Flow<List<City>>
}