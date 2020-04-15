package pl.kamilszustak.hulapp.domain.usecase.comment

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.comment.CommentsSortOrder
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor

interface GetAllCommentsWithAuthorsByPostIdUseCase {
    operator fun invoke(
        postId: Long,
        sortOrder: CommentsSortOrder = CommentsSortOrder.DATE_ASCENDING,
        shouldFetch: Boolean = true
    ): Flow<Resource<List<CommentWithAuthor>>>
}