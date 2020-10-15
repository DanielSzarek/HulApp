package pl.kamilszustak.hulapp.data.repository.post

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.workDataOf
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.PostDao
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.data.repository.WorkManagerRepository
import pl.kamilszustak.hulapp.data.worker.post.AddPostWorker
import pl.kamilszustak.hulapp.domain.mapper.post.PostJsonMapper
import pl.kamilszustak.hulapp.domain.model.network.AddPostRequstBody
import pl.kamilszustak.hulapp.domain.model.network.EditPostRequestBody
import pl.kamilszustak.hulapp.domain.model.post.PostJson
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthorEntity
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.util.adapter
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    application: Application,
    private val postDao: PostDao,
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val postJsonMapper: PostJsonMapper
) : WorkManagerRepository(application) {

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
                    postDao.replaceAll(posts)
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
                userDao.insert(result.author)

                postJsonMapper.onMap(result) { post ->
                    postDao.insert(post)
                }
            }
        }.asFlow()
    }

    fun add(requestBody: AddPostRequstBody): Result<Unit> {
        val moshiAdapter = Moshi.Builder()
            .build()
            .adapter<AddPostRequstBody>()

        val json = moshiAdapter.toJson(requestBody)
        val data = workDataOf(AddPostWorker.REQUEST_BODY_KEY to json)

        val request = OneTimeWorkRequestBuilder<AddPostWorker>()
            .setInputData(data)
            .build()
        
        workManager.enqueue(request)

        return Result.success(Unit)
    }

    suspend fun editById(id: Long, requestBody: EditPostRequestBody): Result<Unit> {
        return object : NetworkCall<PostJson, Unit>() {
            override suspend fun makeCall(): Response<PostJson> =
                apiService.editPostById(id, requestBody)

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