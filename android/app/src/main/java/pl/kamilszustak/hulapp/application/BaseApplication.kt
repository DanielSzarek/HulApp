package pl.kamilszustak.hulapp.application

import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import pl.kamilszustak.hulapp.di.component.ApplicationComponent
import pl.kamilszustak.hulapp.di.component.DaggerApplicationComponent
import pl.kamilszustak.hulapp.di.factory.WorkerFactory
import timber.log.Timber
import javax.inject.Inject

class BaseApplication : DaggerApplication() {
    @Inject
    protected lateinit var workerFactory: WorkerFactory

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
        initializeWorkManager()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        applicationComponent

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initializeWorkManager() {
        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, configuration)
    }
}