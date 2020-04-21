package pl.kamilszustak.hulapp.domain.usecase.comment

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.comment.CommentRepository
import pl.kamilszustak.hulapp.domain.mapper.comment.CommentWithAuthorEntityMapper
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCommentByIdWithAuthorUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository,
    private val commentWithAuthorEntityMapper: CommentWithAuthorEntityMapper
) : GetCommentByIdWithAuthorUseCase {

    override fun invoke(id: Long, shouldFetch: Boolean): Flow<Resource<CommentWithAuthor>> =
        commentRepository.getByIdWithAuthor(id, shouldFetch)
            .map { commentResource ->
                commentResource.mapData { commentWithAuthor ->
                    commentWithAuthorEntityMapper.map(commentWithAuthor)
                }
            }
}