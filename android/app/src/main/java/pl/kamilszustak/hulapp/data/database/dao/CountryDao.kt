package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.data.model.Country

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<Country>)

    @Update
    suspend fun update(country: Country)

    @Delete
    suspend fun delete(country: Country)

    @Query("SELECT * FROM countries")
    fun getAll(): Flow<List<Country>>

    @Query("SELECT * FROM countries WHERE id = :id")
    fun getById(id: Long): Flow<Country>

    @Query("SELECT * FROM countries WHERE name LIKE '%' || :name || '%'")
    fun getByName(name: String): Flow<List<Country>>
}