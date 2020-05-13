package pl.kamilszustak.hulapp.domain.usecase.comment

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor

interface GetCommentByIdWithAuthorUseCase {
    operator fun invoke(id: Long, shouldFetch: Boolean = true): Flow<Resource<CommentWithAuthor>>
}