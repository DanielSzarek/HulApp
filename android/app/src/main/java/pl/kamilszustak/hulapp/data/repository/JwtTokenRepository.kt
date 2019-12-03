package pl.kamilszustak.hulapp.data.repository

import android.app.Application
import androidx.core.content.edit
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.repository.constant.DEFAULT_JWT_ACCESS_TOKEN
import pl.kamilszustak.hulapp.data.repository.constant.DEFAULT_JWT_REFRESH_TOKEN
import pl.kamilszustak.hulapp.data.repository.constant.DEFAULT_JWT_TOKEN_EXPIRATION_DATE_TIMESTAMP
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("UNCHECKED_CAST")
class JwtTokenRepository @Inject constructor(
    application: Application
) : SharedPreferencesRepository<JwtTokenRepository.JwtTokenKey>(
    application,
    "jwt_token_shared_preferences"
) {

    override fun restoreDefaultValues() {
        sharedPreferences.edit {
            clear()
        }
    }

    enum class JwtTokenKey : SharedPreferencesKey {
        ACCESS_TOKEN {
            override fun getStringResourceId(): Int =
                R.string.shared_preferences_jwt_access_token

            override fun <T : Comparable<T>> getDefaultValue(): T =
                DEFAULT_JWT_ACCESS_TOKEN as T
        },

        REFRESH_TOKEN {
            override fun getStringResourceId(): Int =
                R.string.shared_preferences_jwt_refresh_token

            override fun <T : Comparable<T>> getDefaultValue(): T =
                DEFAULT_JWT_REFRESH_TOKEN as T
        },

        TOKEN_EXPIRATION_DATE_TIMESTAMP {
            override fun getStringResourceId(): Int =
                R.string.shared_preferences_jwt_token_expiration_date_timestamp

            override fun <T : Comparable<T>> getDefaultValue(): T =
                DEFAULT_JWT_TOKEN_EXPIRATION_DATE_TIMESTAMP as T
        }
    }
}