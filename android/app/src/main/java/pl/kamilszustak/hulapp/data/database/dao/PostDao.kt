package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.post.PostEntity
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor

@Dao
interface PostDao : BaseDao<PostEntity> {
    @Transaction
    suspend fun replaceAll(posts: Collection<PostEntity>) {
        deleteAll()
        insertAll(posts)
    }

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    @Query("SELECT * FROM posts ORDER BY created_at ASC")
    fun getAllWithAuthorsOrderedByDateAscending(): Flow<List<PostWithAuthor>>

    @Query("SELECT * FROM posts ORDER BY created_at DESC")
    fun getAllWithAuthorsOrderedByDateDescending(): Flow<List<PostWithAuthor>>

    @Query("SELECT * FROM posts ORDER BY created_at ASC")
    fun getAllOrderedByDateAscending(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts ORDER BY created_at DESC")
    fun getAllOrderedByDateDescending(): Flow<List<PostEntity>>
}