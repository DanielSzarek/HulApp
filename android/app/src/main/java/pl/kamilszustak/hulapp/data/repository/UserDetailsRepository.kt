package pl.kamilszustak.hulapp.data.repository

import android.app.Application
import androidx.core.content.edit
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.repository.constant.DEFAULT_USER_EMAIL
import pl.kamilszustak.hulapp.data.repository.constant.DEFAULT_USER_ID
import pl.kamilszustak.hulapp.data.repository.constant.DEFAULT_USER_PASSWORD
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("UNCHECKED_CAST")
class UserDetailsRepository @Inject constructor(
    application: Application
) : SharedPreferencesRepository<UserDetailsRepository.UserDetailsKey>(
    application,
    "user_details_shared_preferences"
) {

    override fun restoreDefaultValues() {
        sharedPreferences.edit {
            clear()
        }
    }

    enum class UserDetailsKey : SharedPreferencesKey {
        USER_EMAIL {
            override fun getStringResourceId(): Int =
                R.string.shared_preferences_user_email

            override fun <T : Comparable<T>> getDefaultValue(): T =
                DEFAULT_USER_EMAIL as T
        },

        USER_PASSWORD {
            override fun getStringResourceId(): Int =
                R.string.shared_preferences_user_password

            override fun <T : Comparable<T>> getDefaultValue(): T =
                DEFAULT_USER_PASSWORD as T
        },

        USER_ID {
            override fun getStringResourceId(): Int =
                R.string.shared_preferences_user_id

            override fun <T : Comparable<T>> getDefaultValue(): T =
                DEFAULT_USER_ID as T
        }
    }
}