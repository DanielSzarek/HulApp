package pl.kamilszustak.hulapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import pl.kamilszustak.hulapp.data.database.ApplicationDatabase
import pl.kamilszustak.hulapp.data.database.dao.*
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): ApplicationDatabase =
        ApplicationDatabase(application)

    @Provides
    @Singleton
    fun provideUserDao(applicationDatabase: ApplicationDatabase): UserDao =
        applicationDatabase.getUserDao()

    @Provides
    @Singleton
    fun provideCityDao(applicationDatabase: ApplicationDatabase): CityDao =
        applicationDatabase.getCityDao()

    @Provides
    @Singleton
    fun provideCountryDao(applicationDatabase: ApplicationDatabase): CountryDao =
        applicationDatabase.getCountryDao()

    @Provides
    @Singleton
    fun provideTrackDao(applicationDatabase: ApplicationDatabase): TrackDao =
        applicationDatabase.getTrackDao()

    @Provides
    @Singleton
    fun provideSearchPromptDao(applicationDatabase: ApplicationDatabase): SearchPromptDao =
        applicationDatabase.getSearchPromptDao()

    @Provides
    @Singleton
    fun providePostDao(applicationDatabase: ApplicationDatabase): PostDao =
        applicationDatabase.getPostDao()

    @Provides
    @Singleton
    fun provideCommentdao(applicationDatabase: ApplicationDatabase): CommentDao =
        applicationDatabase.getCommentDao()

    @Provides
    @Singleton
    fun provideMapPointDao(applicationDatabase: ApplicationDatabase): MapPointDao =
        applicationDatabase.getMapPointDao()
}