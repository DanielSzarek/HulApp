package pl.kamilszustak.hulapp.di.module

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.kamilszustak.hulapp.data.worker.ListenableWorkerFactory
import pl.kamilszustak.hulapp.data.worker.post.AddPostWorker
import pl.kamilszustak.hulapp.di.key.WorkerKey

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(AddPostWorker::class)
    fun bindAddPostWorker(factory: AddPostWorker.Factory): ListenableWorkerFactory
}