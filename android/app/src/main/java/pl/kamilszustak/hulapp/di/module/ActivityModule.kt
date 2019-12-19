package pl.kamilszustak.hulapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kamilszustak.hulapp.di.scope.ActivityScope
import pl.kamilszustak.hulapp.ui.view.authorization.AuthorizationActivity
import pl.kamilszustak.hulapp.ui.view.main.MainActivity
import pl.kamilszustak.hulapp.ui.view.splashscreen.SplashScreenActivity

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeSplashScreenActivity(): SplashScreenActivity

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            AuthorizationActivityModule::class
        ]
    )
    abstract fun contributeAuthorizationActivity(): AuthorizationActivity

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}