package pl.kamilszustak.hulapp.domain.usecase.comment

import pl.kamilszustak.hulapp.domain.model.network.AddCommentRequestBody

interface AddCommentUseCase {
    suspend operator fun invoke(requestBody: AddCommentRequestBody): Result<Unit>
}