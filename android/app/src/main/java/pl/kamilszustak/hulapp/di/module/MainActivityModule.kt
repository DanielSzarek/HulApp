package pl.kamilszustak.hulapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kamilszustak.hulapp.di.scope.FragmentScope
import pl.kamilszustak.hulapp.ui.dialog.city.CityChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.dialog.country.CountryChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.main.event.EventsFragment
import pl.kamilszustak.hulapp.ui.main.feed.FeedFragment
import pl.kamilszustak.hulapp.ui.main.feed.post.add.AddPostFragment
import pl.kamilszustak.hulapp.ui.main.feed.post.details.PostDetailsFragment
import pl.kamilszustak.hulapp.ui.main.feed.post.details.comment.EditCommentFragment
import pl.kamilszustak.hulapp.ui.main.message.MessagesFragment
import pl.kamilszustak.hulapp.ui.main.profile.changepassword.ChangePasswordFragment
import pl.kamilszustak.hulapp.ui.main.profile.edit.EditProfileFragment
import pl.kamilszustak.hulapp.ui.main.profile.main.ProfileFragment
import pl.kamilszustak.hulapp.ui.main.profile.main.photo.fullscreen.ProfilePhotoFullscreenDialogFragment
import pl.kamilszustak.hulapp.ui.main.profile.main.photo.options.ProfilePhotoOptionsBottomSheet
import pl.kamilszustak.hulapp.ui.main.profile.search.SearchFragment
import pl.kamilszustak.hulapp.ui.main.profile.user.UserProfileFragment
import pl.kamilszustak.hulapp.ui.main.tracking.TrackingFragment
import pl.kamilszustak.hulapp.ui.main.tracking.details.TrackDetailsFragment
import pl.kamilszustak.hulapp.ui.main.tracking.details.user.UserTrackDetailsFragment
import pl.kamilszustak.hulapp.ui.main.tracking.history.main.TrackingHistoryFragment
import pl.kamilszustak.hulapp.ui.main.tracking.history.user.UserTrackingHistoryFragment
import pl.kamilszustak.hulapp.ui.main.tracking.point.add.AddMapPointFragment
import pl.kamilszustak.hulapp.ui.main.tracking.point.details.MapPointDetailsFragment

@Module
abstract class MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeEditProfileFragment(): EditProfileFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeProfilePhotoOptionsBottomSheet(): ProfilePhotoOptionsBottomSheet

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeProfilePhotoFullscreenDialogFragment(): ProfilePhotoFullscreenDialogFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeCityChoiceBottomSheet(): CityChoiceBottomSheet

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeCountryChoiceBottomSheet(): CountryChoiceBottomSheet

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeFeedFragment(): FeedFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeEventsFragment(): EventsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeTrackingFragment(): TrackingFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeTrackDetailsFragment(): TrackDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeTrackingHistoryFragment(): TrackingHistoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMessagesFragment(): MessagesFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeUserProfileFragment(): UserProfileFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeUserTrackDetailsFragment(): UserTrackDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeUserTrackingHistoryFragment(): UserTrackingHistoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeAddPostFragment(): AddPostFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributePostDetailsFragment(): PostDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeEditCommentFragment(): EditCommentFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeAddMapPointFragment(): AddMapPointFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMapPointDetailsFragment(): MapPointDetailsFragment
}