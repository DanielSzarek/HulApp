package pl.kamilszustak.hulapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kamilszustak.hulapp.di.scope.FragmentScope
import pl.kamilszustak.hulapp.ui.view.authorization.LoginFragment
import pl.kamilszustak.hulapp.ui.view.authorization.PasswordResetFragment
import pl.kamilszustak.hulapp.ui.view.authorization.SignUpFragment

@Module
abstract class AuthorizationActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSignUpFragment(): SignUpFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributePasswordResetFragment(): PasswordResetFragment
}