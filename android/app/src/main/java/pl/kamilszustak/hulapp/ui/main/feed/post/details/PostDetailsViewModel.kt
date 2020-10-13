package pl.kamilszustak.hulapp.ui.main.feed.post.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor
import pl.kamilszustak.hulapp.domain.model.network.AddCommentRequestBody
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.domain.usecase.comment.AddCommentUseCase
import pl.kamilszustak.hulapp.domain.usecase.comment.DeleteCommentByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.comment.GetAllCommentsWithAuthorsByPostIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.DeletePostByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.GetPostByIdWithAuthorUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.details.ShareEvent
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    application: Application,
    private val getPostByIdWithAuthorUseCase: GetPostByIdWithAuthorUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getAllCommentsWithAuthorsByPostId: GetAllCommentsWithAuthorsByPostIdUseCase,
    private val addComment: AddCommentUseCase,
    private val deleteCommentById: DeleteCommentByIdUseCase
) : StateViewModel(application) {

    val postWithAuthorResource: ResourceDataSource<PostWithAuthor> = ResourceDataSource()
    val commentsWithAuthorsResource: ResourceDataSource<List<CommentWithAuthor>> = ResourceDataSource()

    private val _sharePostEvent: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharePostEvent: LiveData<ShareEvent> = _sharePostEvent

    private val _hideKeyboard: SingleLiveData<Unit> = SingleLiveData()
    val hideKeyboard: LiveData<Unit> = _hideKeyboard

    private val _commentAdded: SingleLiveData<Unit> = SingleLiveData()
    val commentAdded: LiveData<Unit> = _commentAdded

    val commentContent: UniqueLiveData<String> = UniqueLiveData()

    fun loadData(postId: Long) {
        initialize {
            postWithAuthorResource.setFlowSource {
                getPostByIdWithAuthorUseCase(postId)
            }

            commentsWithAuthorsResource.setFlowSource {
                getAllCommentsWithAuthorsByPostId(postId)
            }
        }
    }

    fun onRefresh() {
        postWithAuthorResource.refresh()
        commentsWithAuthorsResource.refresh()
    }

    fun onShareButtonClick(postId: Long) {
        _sharePostEvent.value = ShareEvent(
            "https://hulapp.com/posts/$postId",
            "Shared post",
            "Udostępnij post"
        )
    }

    fun onDeleteButtonClick(postId: Long) {
        performAction(R.string.deleting_post_error_message) {
            deletePostByIdUseCase(postId)
        }
    }

    fun onAddCommentButtonClick(postId: Long) {
        val content = commentContent.value
        if (content.isNullOrBlank()) {
            _errorEvent.value = R.string.empty_comment_content_error_message
            return
        }

        _hideKeyboard.call()

        val requestBody = AddCommentRequestBody(postId, content)
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            val result = addComment(requestBody)
            result.onSuccess {
                _commentAdded.call()
                commentContent.value = null
            }.onFailure {
                _errorEvent.value = R.string.adding_comment_error_message
            }

            _isLoading.value = false
        }
    }

    fun onDeleteCommentButtonClick(commentId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            deleteCommentById(commentId).onFailure {
                _errorEvent.value = R.string.deleting_comment_error_message
            }

            _isLoading.value = false
        }
    }
}