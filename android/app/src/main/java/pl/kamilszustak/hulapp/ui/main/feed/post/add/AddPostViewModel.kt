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
import pl.kamilszustak.hulapp.domain.usecase.post.AddPostUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.GetPostByIdWithAuthorUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class AddPostViewModel @Inject constructor(
    application: Application,
    private val addPostUseCase: AddPostUseCase,
    private val getPostByIdWithAuthorUseCase: GetPostByIdWithAuthorUseCase
) : StateViewModel(application) {

    val postContentField: FormField<String> = formField {
        +Rule<String>("Treść postu nie może być pusta") {
            !it.isBlank()
        }
    }

    private var inEditMode: Boolean = false

    fun loadData(postId: Long) {
        inEditMode = true

        viewModelScope.launch(Dispatchers.Main) {
            getPostByIdWithAuthorUseCase(postId).collect {
                postContentField.data.value = it.data?.content
            }
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