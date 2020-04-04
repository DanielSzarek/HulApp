package pl.kamilszustak.hulapp.ui.main.feed

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.domain.usecase.post.GetAllPostsWithAuthorsUseCase
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    application: Application,
    private val getAllPostsWithAuthorsUseCase: GetAllPostsWithAuthorsUseCase
) : BaseViewModel(application) {

    val postsWithAuthorsResource: ResourceDataSource<List<PostWithAuthor>> = ResourceDataSource()

    init {
        postsWithAuthorsResource.changeFlowSource {
            getAllPostsWithAuthorsUseCase()
        }
    }

    fun onRefresh() {
        postsWithAuthorsResource.refresh()
    }
}