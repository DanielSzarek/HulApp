package pl.kamilszustak.hulapp.ui.main.feed

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.domain.usecase.post.DeletePostByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.post.GetAllPostsWithAuthorsUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.details.ShareEvent
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    application: Application,
    private val getAllPostsWithAuthors: GetAllPostsWithAuthorsUseCase,
    private val deletePostById: DeletePostByIdUseCase
) : StateViewModel(application) {

    val postsWithAuthorsResource: ResourceDataSource<List<PostWithAuthor>> = ResourceDataSource()

    private val _sharePostEvent: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharePostEvent: LiveData<ShareEvent> = _sharePostEvent

    init {
        postsWithAuthorsResource.setFlowSource {
            getAllPostsWithAuthors()
        }
    }

    fun onRefresh() {
        postsWithAuthorsResource.refresh()
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
            deletePostById(postId)
        }
    }
}