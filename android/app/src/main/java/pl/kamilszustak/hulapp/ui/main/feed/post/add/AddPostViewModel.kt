package pl.kamilszustak.hulapp.ui.main.feed.post.add

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.domain.model.network.AddPostRequstBody
import pl.kamilszustak.hulapp.domain.model.network.EditPostRequestBody
import pl.kamilszustak.hulapp.domain.usecase.post.AddPostUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.EditPostByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.GetPostByIdWithAuthorUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class AddPostViewModel @Inject constructor(
    application: Application,
    private val addPostUseCase: AddPostUseCase,
    private val editPostByIdUseCase: EditPostByIdUseCase,
    private val getPostByIdWithAuthorUseCase: GetPostByIdWithAuthorUseCase
) : StateViewModel(application) {

    val postContentField: FormField<String> = formField {
        +Rule<String>("Treść posta nie może być pusta") {
            !it.isBlank()
        }
    }

    private var inEditMode: Boolean = false

    fun loadData(postId: Long) {
        inEditMode = true

        viewModelScope.launch(Dispatchers.Main) {
            getPostByIdWithAuthorUseCase(postId, false).collect {
                postContentField.data.value = it.data?.content
            }
        }
    }

    fun onAddPostButtonClick(postId: Long) {
        val content = postContentField.data.value?.trim()
        if (content.isNullOrBlank()) {
            _errorEvent.value = R.string.empty_post_error_message
            return
        }

        if (inEditMode) {
            val requestBody = EditPostRequestBody(content)
            performAction(R.string.post_editing_error_message) {
                editPostByIdUseCase(postId, requestBody)
            }
        } else {
            val requestBody = AddPostRequstBody(content)
            performAction(R.string.post_adding_error_message) {
                addPostUseCase(requestBody)
            }
        }
    }
}