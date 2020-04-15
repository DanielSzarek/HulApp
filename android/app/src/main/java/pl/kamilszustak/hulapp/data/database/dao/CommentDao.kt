package pl.kamilszustak.hulapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.domain.model.comment.CommentEntity
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthorEntity

@Dao
interface CommentDao : BaseDao<CommentEntity> {
    suspend fun replaceAllByPostId(comments: Collection<CommentEntity>) {
        val postId = comments.firstOrNull()?.postId
        if (postId != null) {
            deleteAllByPostId(postId)
        }

        insertAll(comments)
    }

    @Query("DELETE FROM comments WHERE post_id = :postId")
    suspend fun deleteAllByPostId(postId: Long)

    @Query("SELECT * FROM comments WHERE post_id = :postId ORDER BY creation_date ASC")
    fun getAllWithAuthorsByPostIdOrderedByDateAscending(postId: Long): Flow<List<CommentWithAuthorEntity>>

    @Query("SELECT * FROM comments WHERE post_id = :postId ORDER BY creation_date DESC")
    fun getAllWithAuthorsByPostIdOrderedByDateDescending(postId: Long): Flow<List<CommentWithAuthorEntity>>
}