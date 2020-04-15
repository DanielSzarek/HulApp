package pl.kamilszustak.hulapp.data.repository.comment

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.CommentDao
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.domain.mapper.comment.CommentJsonMapper
import pl.kamilszustak.hulapp.domain.model.comment.CommentJson
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthorEntity
import pl.kamilszustak.hulapp.domain.model.network.AddCommentRequestBody
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor(
    private val commentDao: CommentDao,
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val commentJsonMapper: CommentJsonMapper
) {
    fun getAllWithAuthorsByPostId(
        postId: Long,
        sortOrder: CommentsSortOrder,
        shouldFetch: Boolean
    ): Flow<Resource<List<CommentWithAuthorEntity>>> {
        return object : NetworkBoundResource<List<CommentJson>, List<CommentWithAuthorEntity>>() {
            override fun loadFromDatabase(): Flow<List<CommentWithAuthorEntity>> = when(sortOrder) {
                CommentsSortOrder.DATE_ASCENDING -> commentDao.getAllWithAuthorsByPostIdOrderedByDateAscending(postId)
                CommentsSortOrder.DATE_DESCENDING -> commentDao.getAllWithAuthorsByPostIdOrderedByDateDescending(postId)
            }

            override fun shouldFetch(data: List<CommentWithAuthorEntity>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<CommentJson>> =
                apiService.getAllCommentsByPostId(postId)

            override suspend fun saveFetchResult(result: List<CommentJson>) {
                val authors = result.map { comment ->
                    comment.author
                }
                userDao.insertAll(authors)

                commentJsonMapper.onMapAll(result) { comments ->
                    commentDao.replaceAllByPostId(comments)
                }
            }
        }.asFlow()
    }

    suspend fun add(requestBody: AddCommentRequestBody): Result<Unit> {
        return object : NetworkCall<CommentJson, Unit>() {
            override suspend fun makeCall(): Response<CommentJson> =
                apiService.addComment(requestBody)

            override suspend fun mapResponse(response: CommentJson): Unit = Unit

            override suspend fun saveCallResult(result: CommentJson) {
                userDao.insert(result.author)
                commentJsonMapper.onMap(result) { comment ->
                    commentDao.insert(comment)
                }
            }
        }.callForResponse()
    }
}