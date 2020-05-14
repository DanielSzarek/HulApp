package pl.kamilszustak.hulapp.domain.usecase.point

import pl.kamilszustak.hulapp.data.repository.point.MapPointRepository
import pl.kamilszustak.hulapp.domain.model.network.AddMapPointRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddMapPointUseCaseImpl @Inject constructor(
    private val mapPointRepository: MapPointRepository
) : AddMapPointUseCase {

    override suspend fun invoke(requestBody: AddMapPointRequestBody): Result<Unit> =
        mapPointRepository.add(requestBody)
}