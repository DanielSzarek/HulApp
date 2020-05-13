package pl.kamilszustak.hulapp.domain.usecase.comment

import pl.kamilszustak.hulapp.data.repository.comment.CommentRepository
import pl.kamilszustak.hulapp.domain.model.network.EditCommentRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditCommentByIdUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository
) : EditCommentByIdUseCase {

    override suspend fun invoke(id: Long, requestBody: EditCommentRequestBody): Result<Unit> =
        commentRepository.editById(id, requestBody)
}