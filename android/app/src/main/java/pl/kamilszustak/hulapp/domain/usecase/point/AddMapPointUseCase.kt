package pl.kamilszustak.hulapp.domain.usecase.point

import pl.kamilszustak.hulapp.domain.model.network.AddMapPointRequestBody

interface AddMapPointUseCase {
    suspend operator fun invoke(requestBody: AddMapPointRequestBody): Result<Unit>
}