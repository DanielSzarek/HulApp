package pl.kamilszustak.hulapp.application

import android.app.Application
import leakcanary.AppWatcher
import pl.kamilszustak.hulapp.di.component.ApplicationComponent
import pl.kamilszustak.hulapp.di.component.DaggerApplicationComponent
import timber.log.Timber

class BaseApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        initializeAppWatcher()
        initializeDagger()
        initializeTimber()
    }

    private fun initializeAppWatcher() {
        AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = true)
    }

    private fun initializeDagger() {
        applicationComponent.inject(this)
    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }
}