package pl.kamilszustak.hulapp.domain.usecase.comment

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.comment.CommentRepository
import pl.kamilszustak.hulapp.data.repository.comment.CommentsSortOrder
import pl.kamilszustak.hulapp.domain.mapper.comment.CommentWithAuthorEntityMapper
import pl.kamilszustak.hulapp.domain.model.comment.CommentWithAuthor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllCommentsWithAuthorsByPostIdUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository,
    private val commentWithAuthorEntityMapper: CommentWithAuthorEntityMapper
) : GetAllCommentsWithAuthorsByPostIdUseCase {

    override fun invoke(
        postId: Long,
        sortOrder: CommentsSortOrder,
        shouldFetch: Boolean
    ): Flow<Resource<List<CommentWithAuthor>>> =
        commentRepository.getAllWithAuthorsByPostId(postId, sortOrder, shouldFetch)
            .map { resource ->
                resource.mapData { comments ->
                    commentWithAuthorEntityMapper.mapAll(comments)
                }
            }
}