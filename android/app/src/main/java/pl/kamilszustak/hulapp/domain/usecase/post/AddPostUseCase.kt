package pl.kamilszustak.hulapp.domain.usecase.post

import pl.kamilszustak.hulapp.domain.model.network.AddPostRequstBody

interface AddPostUseCase {
    suspend operator fun invoke(requestBody: AddPostRequstBody): Result<Unit>
}