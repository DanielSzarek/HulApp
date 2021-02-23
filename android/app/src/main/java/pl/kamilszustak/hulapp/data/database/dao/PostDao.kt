package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.post.PostEntity
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthorEntity

@Dao
interface PostDao : BaseDao<PostEntity> {
    @Transaction
    suspend fun replaceAll(posts: Collection<PostEntity>) {
        deleteAll()
        insertAll(posts)
    }

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM posts ORDER BY creation_date ASC")
    @Transaction
    fun getAllWithAuthorsOrderedByDateAscending(): Flow<List<PostWithAuthorEntity>>

    @Query("SELECT * FROM posts ORDER BY creation_date DESC")
    @Transaction
    fun getAllWithAuthorsOrderedByDateDescending(): Flow<List<PostWithAuthorEntity>>

    @Query("SELECT * FROM posts WHERE id = :id")
    @Transaction
    fun getByIdWithAuthor(id: Long): Flow<PostWithAuthorEntity>

    @Query("SELECT * FROM posts ORDER BY creation_date ASC")
    fun getAllOrderedByDateAscending(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts ORDER BY creation_date DESC")
    fun getAllOrderedByDateDescending(): Flow<List<PostEntity>>
}