package pl.kamilszustak.hulapp.data.repository.post

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.PostDao
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.domain.mapper.post.PostJsonMapper
import pl.kamilszustak.hulapp.domain.model.network.AddPostRequstBody
import pl.kamilszustak.hulapp.domain.model.network.EditPostRequestBody
import pl.kamilszustak.hulapp.domain.model.post.PostJson
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthorEntity
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val postDao: PostDao,
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val postJsonMapper: PostJsonMapper
) {
    fun getAllWithAuthors(
        sortOrder: PostsSortOrder,
        shouldFetch: Boolean = true
    ): Flow<Resource<List<PostWithAuthorEntity>>> {
        return object : NetworkBoundResource<List<PostJson>, List<PostWithAuthorEntity>>() {
            override fun loadFromDatabase(): Flow<List<PostWithAuthorEntity>> = when (sortOrder) {
                PostsSortOrder.DATE_ASCENDING -> postDao.getAllWithAuthorsOrderedByDateAscending()
                PostsSortOrder.DATE_DESCENDING -> postDao.getAllWithAuthorsOrderedByDateDescending()
            }

            override fun shouldFetch(data: List<PostWithAuthorEntity>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<PostJson>> =
                apiService.getAllPosts()

            override suspend fun saveFetchResult(result: List<PostJson>) {
                result.map { post ->
                    post.author
                }.let { users ->
                    userDao.insertAll(users)
                }

                postJsonMapper.onMapAll(result) { posts ->
                    postDao.insertAll(posts)
                }
            }
        }.asFlow()
    }

    fun getByIdWithAuthor(
        id: Long,
        shouldFetch: Boolean = true
    ): Flow<Resource<PostWithAuthorEntity>> {
        return object : NetworkBoundResource<PostJson, PostWithAuthorEntity>() {
            override fun loadFromDatabase(): Flow<PostWithAuthorEntity> =
                postDao.getByIdWithAuthor(id)

            override fun shouldFetch(data: PostWithAuthorEntity?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<PostJson> =
                apiService.getPostById(id)

            override suspend fun saveFetchResult(result: PostJson) {
                postJsonMapper.onMap(result) { post ->
                    postDao.insert(post)
                }
            }
        }.asFlow()
    }

    suspend fun add(requestBody: AddPostRequstBody): Result<Unit> {
        return object : NetworkCall<PostJson, Unit>() {
            override suspend fun makeCall(): Response<PostJson> =
                apiService.addPost(requestBody)

            override suspend fun mapResponse(response: PostJson): Unit = Unit

            override suspend fun saveCallResult(result: PostJson) {
                userDao.insert(result.author)
                postJsonMapper.onMap(result) { post ->
                    postDao.insert(post)
                }
            }
        }.callForResponse()
    }

    suspend fun editPost(postId: Long, requestBody: EditPostRequestBody): Result<Unit> {
        return object : NetworkCall<PostJson, Unit>() {
            override suspend fun makeCall(): Response<PostJson> =
                apiService.editPost(postId, requestBody)

            override suspend fun mapResponse(response: PostJson): Unit = Unit

            override suspend fun saveCallResult(result: PostJson) {
                postJsonMapper.onMap(result) { post ->
                    postDao.update(post)
                }
            }
        }.callForResponse()
    }


    suspend fun deleteById(id: Long): Result<Unit> {
        return object : NetworkCall<Unit, Unit>() {
            override suspend fun makeCall(): Response<Unit> =
                apiService.deletePostById(id)

            override suspend fun mapResponse(response: Unit): Unit = Unit

            override suspend fun onResponseSuccess() {
                postDao.deleteById(id)
            }
        }.call()
    }
}