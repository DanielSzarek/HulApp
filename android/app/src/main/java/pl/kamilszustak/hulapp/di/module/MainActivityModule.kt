package pl.kamilszustak.hulapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kamilszustak.hulapp.di.scope.FragmentScope
import pl.kamilszustak.hulapp.ui.view.main.profile.EditProfileFragment
import pl.kamilszustak.hulapp.ui.view.main.profile.ProfileFragment

@Module
abstract class MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeEditProfileFragment(): EditProfileFragment
}