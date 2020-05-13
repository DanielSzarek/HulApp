package pl.kamilszustak.hulapp.ui.main.feed.post.details.comment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.form.FormField
import pl.kamilszustak.hulapp.common.form.Rule
import pl.kamilszustak.hulapp.common.form.formField
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor
import pl.kamilszustak.hulapp.domain.model.network.EditCommentRequestBody
import pl.kamilszustak.hulapp.domain.usecase.comment.DeleteCommentByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.comment.EditCommentByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.comment.GetCommentByIdWithAuthorUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class EditCommentViewModel @Inject constructor(
    application: Application,
    private val getCommentByIdWithAuthor: GetCommentByIdWithAuthorUseCase,
    private val editCommentById: EditCommentByIdUseCase,
    private val deleteCommentById: DeleteCommentByIdUseCase
) : StateViewModel(application) {

    val commentContentField: FormField<String> = formField {
        +Rule<String>("Treść komentarza nie może być pusta") {
            !it.isBlank()
        }
    }

    private val _commentWithAuthor: UniqueLiveData<CommentWithAuthor> = UniqueLiveData()
    val commentWithAuthor: LiveData<CommentWithAuthor> = _commentWithAuthor

    fun loadData(commentId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            getCommentByIdWithAuthor(commentId, false).collect { commentResource ->
                _commentWithAuthor.value = commentResource.data
                commentContentField.data.value = commentResource.data?.content
            }
        }
    }

    fun onDeleteCommentButtonClick(commentId: Long) {
        performAction(R.string.deleting_comment_error_message) {
            deleteCommentById(commentId)
        }
    }

    fun onEditCommentButtonClick(commentId: Long) {
        val content = commentContentField.data.value
        if (content.isNullOrBlank()) {
            _errorEvent.value = R.string.empty_comment_content_error_message
            return
        }

        val requestBody = EditCommentRequestBody(content)
        performAction(R.string.editing_comment_error_message) {
            editCommentById(commentId, requestBody)
        }
    }
}