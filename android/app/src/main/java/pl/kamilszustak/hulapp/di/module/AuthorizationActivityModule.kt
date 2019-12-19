package pl.kamilszustak.hulapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kamilszustak.hulapp.di.scope.FragmentScope
import pl.kamilszustak.hulapp.ui.view.authorization.LoginFragment
import pl.kamilszustak.hulapp.ui.view.authorization.PasswordResetFragment
import pl.kamilszustak.hulapp.ui.view.authorization.SignUpFragment
import pl.kamilszustak.hulapp.ui.view.dialog.CityChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.view.dialog.CountryChoiceBottomSheet

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

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeCityChoiceBottomSheet(): CityChoiceBottomSheet

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeCountryChoiceBottomSheet(): CountryChoiceBottomSheet
}