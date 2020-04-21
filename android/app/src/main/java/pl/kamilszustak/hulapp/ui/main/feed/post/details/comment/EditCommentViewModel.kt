package pl.kamilszustak.hulapp.ui.main.feed.post.details.comment

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.domain.usecase.comment.DeleteCommentByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.comment.EditCommentByIdUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class EditCommentViewModel @Inject constructor(
    application: Application,
    private val editCommentById: EditCommentByIdUseCase,
    private val deleteCommentById: DeleteCommentByIdUseCase
) : StateViewModel(application) {

    val commentContent: UniqueLiveData<String> = UniqueLiveData()

    fun loadData(commentId: Long) {
        viewModelScope.launch(Dispatchers.Main) {

            getPostByIdWithAuthorUseCase(postId, false).collect {
                postContentField.data.value = it.data?.content
            }
        }
    }
}