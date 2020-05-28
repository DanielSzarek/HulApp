package pl.kamilszustak.hulapp.domain.usecase.point

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.domain.model.point.MapPoint

interface GetAllMapPointsUseCase {
    operator fun invoke(shouldFetch: Boolean = true): Flow<Resource<List<MapPoint>>>
}