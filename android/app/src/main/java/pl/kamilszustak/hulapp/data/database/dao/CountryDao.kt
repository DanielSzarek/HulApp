package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.Country

@Dao
interface CountryDao : BaseDao<Country> {
    @Query("SELECT * FROM countries")
    fun getAll(): Flow<List<Country>>

    @Query("SELECT * FROM countries WHERE id = :id")
    fun getById(id: Long): Flow<Country>

    @Query(
        "SELECT * FROM countries WHERE LOWER(name) LIKE LOWER(:text) || '%'" +
                "OR LOWER(name) LIKE '% ' || LOWER(:text) || '%'" +
                "OR LOWER(name) LIKE '%-' || LOWER(:text) || '%'"
    )
    fun getByNameContaining(text: String): Flow<List<Country>>
}