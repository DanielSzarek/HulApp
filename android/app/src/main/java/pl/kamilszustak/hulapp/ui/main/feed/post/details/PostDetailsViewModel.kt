package pl.kamilszustak.hulapp.ui.main.feed.post.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.domain.usecase.post.DeletePostByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.GetPostByIdWithAuthorUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.details.ShareEvent
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    application: Application,
    private val getPostByIdWithAuthorUseCase: GetPostByIdWithAuthorUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase
) : StateViewModel(application) {

    val postWithAuthorResource: ResourceDataSource<PostWithAuthor> = ResourceDataSource()

    private val _sharePostEvent: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharePostEvent: LiveData<ShareEvent> = _sharePostEvent

    fun loadData(postId: Long) {
        postWithAuthorResource.changeFlowSource {
            getPostByIdWithAuthorUseCase(postId)
        }
    }

    fun onRefresh() {
        postWithAuthorResource.refresh()
    }

    fun onShareButtonClick(postId: Long) {
        _sharePostEvent.value = ShareEvent(
            "http://hulapp.com/posts/$postId",
            "Shared post",
            "Udostępnij post"
        )
    }

    fun onDeleteButtonClick(postId: Long) {
        performAction(R.string.deleting_post_error_message) {
            deletePostByIdUseCase(postId)
        }
    }
}