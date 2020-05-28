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
class GetMapPointByIdUseCaseImpl @Inject constructor(
    private val mapPointRepository: MapPointRepository,
    private val mapPointWithAuthorMapper: MapPointWithAuthorMapper
) : GetMapPointByIdUseCase {

    override fun invoke(id: Long, shouldFetch: Boolean): Flow<Resource<MapPoint>> =
        mapPointRepository.getById(id, shouldFetch)
            .map { resource ->
                resource.mapData { point ->
                    mapPointWithAuthorMapper.map(point)
                }
            }
}