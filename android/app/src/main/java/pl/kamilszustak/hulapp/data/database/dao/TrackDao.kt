package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.data.model.Track

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<Track>)

    @Delete
    suspend fun delete(track: Track)

    @Query("DELETE FROM tracks WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM tracks")
    fun getAll(): Flow<List<Track>>

    @Query("SELECT * FROM tracks WHERE id = :id")
    fun getById(id: Long): Flow<Track>
}