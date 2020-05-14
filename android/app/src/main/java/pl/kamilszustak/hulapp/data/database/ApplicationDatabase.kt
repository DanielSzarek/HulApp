package pl.kamilszustak.hulapp.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.security.crypto.MasterKeys
import com.commonsware.cwac.saferoom.SafeHelperFactory
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.database.dao.*
import pl.kamilszustak.hulapp.domain.model.City
import pl.kamilszustak.hulapp.domain.model.Country
import pl.kamilszustak.hulapp.domain.model.SearchPrompt
import pl.kamilszustak.hulapp.domain.model.User
import pl.kamilszustak.hulapp.domain.model.comment.CommentEntity
import pl.kamilszustak.hulapp.domain.model.point.MapPointEntity
import pl.kamilszustak.hulapp.domain.model.post.PostEntity
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity

@Database(
    entities = [
        User::class,
        City::class,
        Country::class,
        TrackEntity::class,
        SearchPrompt::class,
        PostEntity::class,
        CommentEntity::class,
        MapPointEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getCityDao(): CityDao
    abstract fun getCountryDao(): CountryDao
    abstract fun getTrackDao(): TrackDao
    abstract fun getSearchPromptDao(): SearchPromptDao
    abstract fun getPostDao(): PostDao
    abstract fun getCommentDao(): CommentDao
    abstract fun getMapPointDao(): MapPointDao

    companion object {
        private var INSTANCE: ApplicationDatabase? = null

        operator fun invoke(application: Application): ApplicationDatabase {
            return INSTANCE ?: synchronized(ApplicationDatabase::class) {
                build(application).also {
                    INSTANCE = it
                }
            }
        }

        private fun build(application: Application): ApplicationDatabase {
            val alias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            val factory = SafeHelperFactory(alias.toByteArray())

            return Room.databaseBuilder(
                application.applicationContext,
                ApplicationDatabase::class.java,
                application.applicationContext.getString(R.string.database_name)
            )
                .openHelperFactory(factory)
                .build()
        }
    }
}