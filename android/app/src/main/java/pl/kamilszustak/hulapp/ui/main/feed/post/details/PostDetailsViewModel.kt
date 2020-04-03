package pl.kamilszustak.hulapp.ui.main.feed.post.details

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.domain.model.post.PostWithAuthor
import pl.kamilszustak.hulapp.domain.usecase.post.GetPostByIdWithAuthorUseCase
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    application: Application,
    private val getPostByIdWithAuthorUseCase: GetPostByIdWithAuthorUseCase
) : BaseViewModel(application) {

    val postWithAuthorResource: ResourceDataSource<PostWithAuthor> = ResourceDataSource()

    fun loadData(postId: Long) {
        postWithAuthorResource.changeFlowSource {
            getPostByIdWithAuthorUseCase(postId)
        }
    }
}