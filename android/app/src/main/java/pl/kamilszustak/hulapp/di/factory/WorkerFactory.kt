package pl.kamilszustak.hulapp.di.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import pl.kamilszustak.hulapp.data.worker.ListenableWorkerFactory
import javax.inject.Inject
import javax.inject.Provider

class WorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ListenableWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        context: Context,
        workerClassName: String,
        parameters: WorkerParameters
    ): ListenableWorker? {
        val foundEntry = workerFactories.entries.find {
            Class.forName(workerClassName)
                .isAssignableFrom(it.key)
        }

        val factoryProvider = foundEntry?.value
            ?: throw IllegalArgumentException("Unknown worker class name: $workerClassName")

        return factoryProvider.get()
            .create(context, parameters)
    }
}