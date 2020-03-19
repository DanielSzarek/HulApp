package pl.kamilszustak.hulapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kamilszustak.hulapp.di.scope.FragmentScope
import pl.kamilszustak.hulapp.ui.authentication.login.LoginFragment
import pl.kamilszustak.hulapp.ui.authentication.passwordreset.PasswordResetCompletedFragment
import pl.kamilszustak.hulapp.ui.authentication.passwordreset.PasswordResetFragment
import pl.kamilszustak.hulapp.ui.authentication.signup.SignUpCompletedFragment
import pl.kamilszustak.hulapp.ui.authentication.signup.SignUpFragment
import pl.kamilszustak.hulapp.ui.dialog.city.CityChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.dialog.country.CountryChoiceBottomSheet

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
    abstract fun contributeSignUpCompletedFragment(): SignUpCompletedFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributePasswordResetFragment(): PasswordResetFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributePasswordResetCompletedFragment(): PasswordResetCompletedFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeCityChoiceBottomSheet(): CityChoiceBottomSheet

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeCountryChoiceBottomSheet(): CountryChoiceBottomSheet
}