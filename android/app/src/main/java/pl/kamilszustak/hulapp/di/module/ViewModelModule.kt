package pl.kamilszustak.hulapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.kamilszustak.hulapp.di.ViewModelKey
import pl.kamilszustak.hulapp.common.AndroidViewModelFactory
import pl.kamilszustak.hulapp.ui.authorization.login.LoginViewModel
import pl.kamilszustak.hulapp.ui.authorization.passwordreset.PasswordResetViewModel
import pl.kamilszustak.hulapp.ui.authorization.signup.SignUpViewModel
import pl.kamilszustak.hulapp.ui.dialog.city.CityChoiceViewModel
import pl.kamilszustak.hulapp.ui.dialog.country.CountryChoiceViewModel
import pl.kamilszustak.hulapp.ui.main.profile.EditProfileViewModel
import pl.kamilszustak.hulapp.ui.main.profile.ProfileViewModel

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindAndroidViewModelFactory(factory: AndroidViewModelFactory): ViewModelProvider.AndroidViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordResetViewModel::class)
    abstract fun bindPasswordResetViewModel(passwordResetViewModel: PasswordResetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    abstract fun bindEditProfileViewModel(editProfileViewModel: EditProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityChoiceViewModel::class)
    abstract fun bindCityChoiceViewModel(cityChoiceViewModel: CityChoiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountryChoiceViewModel::class)
    abstract fun bindCountryChoiceViewModel(countryChoiceViewModel: CountryChoiceViewModel): ViewModel
}