package pl.kamilszustak.hulapp.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import pl.kamilszustak.hulapp.application.BaseApplication
import pl.kamilszustak.hulapp.di.module.ApplicationModule
import pl.kamilszustak.hulapp.di.module.DatabaseModule
import pl.kamilszustak.hulapp.di.module.NetworkModule
import pl.kamilszustak.hulapp.ui.viewmodel.BaseViewModel
import pl.kamilszustak.hulapp.ui.viewmodel.authorization.LoginViewModel
import pl.kamilszustak.hulapp.ui.viewmodel.authorization.PasswordResetViewModel
import pl.kamilszustak.hulapp.ui.viewmodel.authorization.SignUpViewModel
import pl.kamilszustak.hulapp.ui.viewmodel.dialog.CityChoiceViewModel
import pl.kamilszustak.hulapp.ui.viewmodel.dialog.CountryChoiceViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    DatabaseModule::class,
    NetworkModule::class
])
interface ApplicationComponent : AndroidInjector<BaseApplication> {

    fun inject(baseViewModel: BaseViewModel)

    fun inject(signUpViewModel: SignUpViewModel)

    fun inject(loginViewModel: LoginViewModel)

    fun inject(passwordResetViewModel: PasswordResetViewModel)

    fun inject(countryChoiceViewModel: CountryChoiceViewModel)

    fun inject(cityChoiceViewModel: CityChoiceViewModel)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}