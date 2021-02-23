package pl.kamilszustak.hulapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.kamilszustak.hulapp.di.factory.AndroidViewModelFactory
import pl.kamilszustak.hulapp.di.key.ViewModelKey
import pl.kamilszustak.hulapp.ui.authentication.login.LoginViewModel
import pl.kamilszustak.hulapp.ui.authentication.passwordreset.PasswordResetViewModel
import pl.kamilszustak.hulapp.ui.authentication.signup.SignUpViewModel
import pl.kamilszustak.hulapp.ui.dialog.city.CityChoiceViewModel
import pl.kamilszustak.hulapp.ui.dialog.country.CountryChoiceViewModel
import pl.kamilszustak.hulapp.ui.main.feed.FeedViewModel
import pl.kamilszustak.hulapp.ui.main.feed.post.add.AddPostViewModel
import pl.kamilszustak.hulapp.ui.main.feed.post.details.PostDetailsViewModel
import pl.kamilszustak.hulapp.ui.main.feed.post.details.comment.EditCommentViewModel
import pl.kamilszustak.hulapp.ui.main.profile.changepassword.ChangePasswordViewModel
import pl.kamilszustak.hulapp.ui.main.profile.edit.EditProfileViewModel
import pl.kamilszustak.hulapp.ui.main.profile.main.ProfileViewModel
import pl.kamilszustak.hulapp.ui.main.profile.main.photo.options.ProfilePhotoOptionsViewModel
import pl.kamilszustak.hulapp.ui.main.profile.search.SearchViewModel
import pl.kamilszustak.hulapp.ui.main.profile.user.UserProfileViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.TrackingViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.details.TrackDetailsViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.details.user.UserTrackDetailsViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.history.main.TrackingHistoryViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.history.user.UserTrackingHistoryViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.point.add.AddMapPointViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.point.details.MapPointDetailsViewModel

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
    @ViewModelKey(ChangePasswordViewModel::class)
    abstract fun bindChangePasswordViewModel(changePasswordViewModel: ChangePasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfilePhotoOptionsViewModel::class)
    abstract fun bindProfilePhotoOptionsViewModel(profilePhotoOptionsViewModel: ProfilePhotoOptionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityChoiceViewModel::class)
    abstract fun bindCityChoiceViewModel(cityChoiceViewModel: CityChoiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountryChoiceViewModel::class)
    abstract fun bindCountryChoiceViewModel(countryChoiceViewModel: CountryChoiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrackingViewModel::class)
    abstract fun bindTrackingViewModel(trackingViewModel: TrackingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrackDetailsViewModel::class)
    abstract fun bindTrackDetailsViewModel(trackDetailsViewModel: TrackDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrackingHistoryViewModel::class)
    abstract fun bindTrackingHistoryViewModel(trackingHistoryViewModel: TrackingHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindUserProfileViewModel(userProfileViewModel: UserProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserTrackDetailsViewModel::class)
    abstract fun bindUserTrackDetailsViewModel(userTrackDetailsViewModel: UserTrackDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserTrackingHistoryViewModel::class)
    abstract fun bindUserTrackingHistoryViewModel(userTrackingHistoryViewModel: UserTrackingHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(feedViewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPostViewModel::class)
    abstract fun bindAddPostViewModel(addPostViewModel: AddPostViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailsViewModel::class)
    abstract fun bindPostDetailsViewModel(postDetailsViewModel: PostDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditCommentViewModel::class)
    abstract fun bindEditCommentViewModel(editCommentViewModel: EditCommentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddMapPointViewModel::class)
    abstract fun bindAddMapPointViewModel(addMapPointViewModel: AddMapPointViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapPointDetailsViewModel::class)
    abstract fun bindMapPointDetailsViewModel(mapPointDetailsViewModel: MapPointDetailsViewModel): ViewModel
}