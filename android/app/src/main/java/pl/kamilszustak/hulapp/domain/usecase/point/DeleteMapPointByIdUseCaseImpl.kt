package pl.kamilszustak.hulapp.domain.usecase.point

import pl.kamilszustak.hulapp.data.repository.point.MapPointRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteMapPointByIdUseCaseImpl @Inject constructor(
    private val mapPointRepository: MapPointRepository
) : DeleteMapPointByIdUseCase {

    override suspend fun invoke(id: Long): Result<Unit> =
        mapPointRepository.deleteById(id)
}