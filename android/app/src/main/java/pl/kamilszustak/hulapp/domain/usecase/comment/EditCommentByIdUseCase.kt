package pl.kamilszustak.hulapp.domain.usecase.comment

import pl.kamilszustak.hulapp.domain.model.network.EditCommentRequestBody

interface EditCommentByIdUseCase {
    suspend operator fun invoke(id: Long, requestBody: EditCommentRequestBody): Result<Unit>
}