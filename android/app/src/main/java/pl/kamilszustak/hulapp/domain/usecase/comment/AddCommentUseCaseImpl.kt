package pl.kamilszustak.hulapp.domain.usecase.comment

import pl.kamilszustak.hulapp.data.repository.comment.CommentRepository
import pl.kamilszustak.hulapp.domain.model.network.AddCommentRequestBody
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddCommentUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : AddCommentUseCase {

    override suspend fun invoke(requestBody: AddCommentRequestBody): Result<Unit> = withIOContext {
        commentRepository.add(requestBody)
    }
}