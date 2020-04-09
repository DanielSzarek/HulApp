package pl.kamilszustak.hulapp.ui.main.feed.post.add

import android.app.Application
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.domain.model.network.AddPostRequstBody
import pl.kamilszustak.hulapp.domain.usecase.post.AddPostUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class AddPostViewModel @Inject constructor(
    application: Application,
    private val addPostUseCase: AddPostUseCase
) : StateViewModel(application) {

    val postContentField: FormField<String> = formField {
        +Rule<String>("Treść postu nie może być pusta") {
            !it.isBlank()
        }
    }

    fun onAddPostButtonClick() {
        val content = postContentField.data.value?.trim()
        if (content.isNullOrBlank()) {
            _error.value = R.string.empty_post_error_message
            return
        }

        val requestBody = AddPostRequstBody(content)
        performAction(R.string.adding_post_error_message) {
            addPostUseCase(requestBody)
        }
    }
}