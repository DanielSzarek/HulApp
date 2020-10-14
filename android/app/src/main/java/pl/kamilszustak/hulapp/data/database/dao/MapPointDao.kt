package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.point.MapPointEntity
import pl.kamilszustak.hulapp.domain.model.point.MapPointWithAuthor

@Dao
interface MapPointDao : BaseDao<MapPointEntity> {
    @Query("SELECT * FROM map_points")
    fun getAll(): Flow<List<MapPointEntity>>

    @Query("SELECT * FROM map_points")
    @Transaction
    fun getAllWithAuthors(): Flow<List<MapPointWithAuthor>>

    @Query("SELECT * FROM map_points WHERE id = :id")
    fun getById(id: Long): Flow<MapPointEntity>

    @Query("SELECT * FROM map_points WHERE id = :id")
    @Transaction
    fun getByIdWithAuthor(id: Long): Flow<MapPointWithAuthor>

    @Transaction
    suspend fun replaceAll(points: Collection<MapPointEntity>) {
        deleteAll()
        insertAll(points)
    }

    @Query("DELETE FROM map_points")
    suspend fun deleteAll()

    @Query("DELETE FROM map_points WHERE id = :id")
    suspend fun deleteById(id: Long)
}