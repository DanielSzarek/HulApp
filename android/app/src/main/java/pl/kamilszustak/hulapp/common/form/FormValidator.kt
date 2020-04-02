package pl.kamilszustak.hulapp.common.form

import android.util.Patterns
import pl.kamilszustak.hulapp.domain.form.Email
import pl.kamilszustak.hulapp.domain.form.Password
import java.util.regex.Pattern
import javax.inject.Inject

class FormValidator @Inject constructor() {

    fun validate(email: Email): Boolean {
        return Patterns
            .EMAIL_ADDRESS
            .matcher(email.value)
            .matches()
    }

    /**
     * Minimum eight characters, at least one letter, one number and one special character

     */
    fun validate(password: Password): Boolean {
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"

        return Pattern
            .compile(regex)
            .matcher(password.value)
            .matches()
    }
}