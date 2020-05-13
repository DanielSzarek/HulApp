package pl.kamilszustak.hulapp.domain.usecase.comment

import pl.kamilszustak.hulapp.data.repository.comment.CommentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteCommentByIdUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : DeleteCommentByIdUseCase {

    override suspend fun invoke(id: Long): Result<Unit> =
        commentRepository.deleteById(id)
}