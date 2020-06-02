package pl.kamilszustak.hulapp.data.worker.post

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import pl.kamilszustak.hulapp.data.database.dao.PostDao
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.data.worker.ListenableWorkerFactory
import pl.kamilszustak.hulapp.network.ApiService

class AddPostWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters,
    private val postDao: PostDao,
    private val userDao: UserDao,
    private val apiService: ApiService
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        val data = Data.Builder()
        data.parce

        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory : ListenableWorkerFactory
}