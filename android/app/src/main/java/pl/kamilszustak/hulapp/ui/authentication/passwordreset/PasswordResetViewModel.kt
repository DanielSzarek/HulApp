package pl.kamilszustak.hulapp.ui.authentication.passwordreset

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.FormValidator
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.domain.form.Email
import pl.kamilszustak.hulapp.manager.AuthorizationManager
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class PasswordResetViewModel @Inject constructor(
    application: Application,
    private val validator: FormValidator,
    private val authorizationManager: AuthorizationManager
) : StateViewModel(application) {

    val userEmail: FormField<String> = formField {
        +Rule<String>("Nieprawidłowy format") {
            it.isNotBlank() && validator.validate(Email(it))
        }
    }

    val isPasswordResetEnabled: LiveData<Boolean> = FormField.validateFields(userEmail)

    fun onPasswordResetButtonClick() {
        val email = userEmail.data.value

        if (email == null) {
            _errorEvent.value = R.string.empty_email_error_message
            return
        }

        performAction(R.string.password_reset_error_message) {
            authorizationManager.resetPassword(email)
        }
    }
}