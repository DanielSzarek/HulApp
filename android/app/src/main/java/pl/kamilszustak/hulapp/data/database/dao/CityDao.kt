package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.City

@Dao
interface CityDao : BaseDao<City> {
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