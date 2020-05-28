package pl.kamilszustak.hulapp.domain.usecase.point

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.repository.point.MapPointRepository
import pl.kamilszustak.hulapp.domain.mapper.point.MapPointWithAuthorMapper
import pl.kamilszustak.hulapp.domain.model.point.MapPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllMapPointsUseCaseImpl @Inject constructor(
    private val mapPointRepository: MapPointRepository,
    private val mapPointWithAuthorMapper: MapPointWithAuthorMapper
) : GetAllMapPointsUseCase {

    override fun invoke(shouldFetch: Boolean): Flow<Resource<List<MapPoint>>> =
        mapPointRepository.getAll(shouldFetch)
            .map { resource ->
                resource.mapData { points ->
                    mapPointWithAuthorMapper.mapAll(points)
                }
            }
}