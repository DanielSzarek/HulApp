package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity

@Dao
interface TrackDao : BaseDao<TrackEntity> {
    @Query("DELETE FROM tracks WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM tracks WHERE user_id = :userId LIMIT :limit")
    fun getAllByUserId(userId: Long, limit: Int): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE id = :id")
    fun getById(id: Long): Flow<TrackEntity>

    @Query("DELETE FROM tracks WHERE user_id = :userId")
    fun deleteAllByUserId(userId: Long)

    @Transaction
    suspend fun replaceAllByUserId(userId: Long, tracks: Collection<TrackEntity>) {
        deleteAllByUserId(userId)
        insertAll(tracks)
    }
}