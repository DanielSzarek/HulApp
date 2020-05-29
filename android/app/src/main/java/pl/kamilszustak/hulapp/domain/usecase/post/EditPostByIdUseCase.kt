package pl.kamilszustak.hulapp.domain.usecase.post

import pl.kamilszustak.hulapp.domain.model.network.EditPostRequestBody

interface EditPostByIdUseCase {
    suspend operator fun invoke(postId: Long, requestBody: EditPostRequestBody): Result<Unit>
}