package pl.kamilszustak.hulapp.ui.main.feed

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.domain.usecase.post.DeletePostByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.GetAllPostsWithAuthorsUseCase
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.details.ShareEvent
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    application: Application,
    private val getAllPostsWithAuthorsUseCase: GetAllPostsWithAuthorsUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase
) : BaseViewModel(application) {

    val postsWithAuthorsResource: ResourceDataSource<List<PostWithAuthor>> = ResourceDataSource()

    private val _sharePostEvent: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharePostEvent: LiveData<ShareEvent> = _sharePostEvent

    private val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: SingleLiveData<Int> = SingleLiveData()
    val error: LiveData<Int> = _error

    init {
        postsWithAuthorsResource.changeFlowSource {
            getAllPostsWithAuthorsUseCase()
        }
    }

    fun onRefresh() {
        postsWithAuthorsResource.refresh()
    }

    fun onShareButtonClick(postId: Long) {
        _sharePostEvent.value = ShareEvent(
            "http://hulapp.com/posts/$postId",
            "Shared post",
            "Udostępnij post"
        )
    }

    fun onDeleteButtonClick(postId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            val result = withIOContext {
                deletePostByIdUseCase(postId)
            }

            result.onFailure {
                _error.value = R.string.deleting_post_error_message
            }

            _isLoading.value = false
        }
    }
}