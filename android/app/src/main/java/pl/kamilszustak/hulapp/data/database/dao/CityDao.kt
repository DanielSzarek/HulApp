package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.data.model.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<City>)

    @Update
    suspend fun update(city: City)

    @Delete
    suspend fun delete(city: City)

    @Query("SELECT * FROM cities")
    fun getAll(): Flow<List<City>>

    @Query("SELECT * FROM cities WHERE id = :id")
    fun getById(id: Long): Flow<City>

    @Query(
        "SELECT * FROM cities WHERE name LIKE :name || '%'" +
                "OR name LIKE '% ' || :name || '%'" +
                "OR name LIKE '%-' || :name || '%'"
    )
    fun getByName(name: String): Flow<List<City>>
}