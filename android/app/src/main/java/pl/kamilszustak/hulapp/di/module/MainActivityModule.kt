package pl.kamilszustak.hulapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kamilszustak.hulapp.di.scope.FragmentScope
import pl.kamilszustak.hulapp.ui.dialog.city.CityChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.dialog.country.CountryChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.main.event.EventsFragment
import pl.kamilszustak.hulapp.ui.main.feed.FeedFragment
import pl.kamilszustak.hulapp.ui.main.message.MessagesFragment
import pl.kamilszustak.hulapp.ui.main.profile.EditProfileFragment
import pl.kamilszustak.hulapp.ui.main.profile.ProfileFragment
import pl.kamilszustak.hulapp.ui.main.track.TracksFragment

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
    abstract fun contributeTracksFragment(): TracksFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMessagesFragment(): MessagesFragment
}