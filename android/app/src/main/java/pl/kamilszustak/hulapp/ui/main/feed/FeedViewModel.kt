package pl.kamilszustak.hulapp.ui.main.feed

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.domain.usecase.post.GetAllPostsWithAuthorsUseCase
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.ui.main.tracking.details.ShareEvent
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    application: Application,
    private val getAllPostsWithAuthorsUseCase: GetAllPostsWithAuthorsUseCase
) : BaseViewModel(application) {

    val postsWithAuthorsResource: ResourceDataSource<List<PostWithAuthor>> = ResourceDataSource()

    private val _sharePostEvent: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharePostEvent: LiveData<ShareEvent> = _sharePostEvent

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
}