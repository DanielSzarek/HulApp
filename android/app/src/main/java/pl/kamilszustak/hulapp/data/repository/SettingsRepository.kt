package pl.kamilszustak.hulapp.data.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.constant.DEFAULT_LOGGED_IN_USER_ID
import pl.kamilszustak.hulapp.constant.DEFAULT_IS_USER_LOGGED_IN
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class SettingsRepository @Inject constructor(
    application: Application
) : SharedPreferencesRepository<SettingsRepository.SettingsKey>(
    application,
    "settings_shared_preferences"
) {

    override fun restoreDefaultValues() {
        sharedPreferences.edit {
            var key = getStringFromResources(SettingsKey.IS_USER_LOGGED_IN.getStringResourceId())
            putBoolean(key, DEFAULT_IS_USER_LOGGED_IN)

            key = getStringFromResources(SettingsKey.LOGGED_IN_USER_ID.getStringResourceId())
            putLong(key, DEFAULT_LOGGED_IN_USER_ID)
        }
    }
    enum class SettingsKey : SharedPreferencesKey {
        IS_USER_LOGGED_IN {
            override fun getStringResourceId(): Int = R.string.shared_preferences_is_user_logged_in

            override fun <T : Comparable<T>> getDefaultValue(): T = DEFAULT_IS_USER_LOGGED_IN as T
        },

        LOGGED_IN_USER_ID {
            override fun getStringResourceId(): Int = R.string.shared_preferences_logged_in_user_id

            override fun <T : Comparable<T>> getDefaultValue(): T = DEFAULT_LOGGED_IN_USER_ID as T
        }
    }
}