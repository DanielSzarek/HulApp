package pl.kamilszustak.hulapp.data.database

import android.app.Application
import androidx.room.*
import androidx.security.crypto.MasterKeys
import com.commonsware.cwac.saferoom.SafeHelperFactory
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.database.dao.*
import pl.kamilszustak.hulapp.data.model.*

@Database(
    entities = [
        User::class,
        City::class,
        Country::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getCityDao(): CityDao

    abstract fun getCountryDao(): CountryDao

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