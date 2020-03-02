package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.data.model.Track

@Dao
interface TrackDao : BaseDao<Track> {
    @Query("DELETE FROM tracks WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM tracks")
    fun getAll(): Flow<List<Track>>

    @Query("SELECT * FROM tracks WHERE id = :id")
    fun getById(id: Long): Flow<Track>
}