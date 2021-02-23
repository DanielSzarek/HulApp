package pl.kamilszustak.hulapp.data.worker.post

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.squareup.moshi.Moshi
import pl.kamilszustak.hulapp.data.database.dao.PostDao
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.data.worker.ListenableWorkerFactory
import pl.kamilszustak.hulapp.domain.mapper.post.PostJsonMapper
import pl.kamilszustak.hulapp.domain.model.network.AddPostRequstBody
import pl.kamilszustak.hulapp.network.ApiService
import pl.kamilszustak.hulapp.util.adapter

class AddPostWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters,
    private val postDao: PostDao,
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val postJsonMapper: PostJsonMapper
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        val json = inputData.getString(REQUEST_BODY_KEY) ?: return Result.failure()
        val requestBody = Moshi.Builder()
            .build()
            .adapter<AddPostRequstBody>()
            .fromJson(json) ?: return Result.failure()

        val response = apiService.addPost(requestBody)
        val body = response.body()
        if (!response.isSuccessful || body == null) {
            return Result.failure()
        }

        userDao.insert(body.author)
        postJsonMapper.onMap(body) { post ->
            postDao.insert(post)
        }

        return Result.success()
    }

    companion object {
        const val REQUEST_BODY_KEY: String = "request_body"
    }

    @AssistedInject.Factory
    interface Factory : ListenableWorkerFactory
}