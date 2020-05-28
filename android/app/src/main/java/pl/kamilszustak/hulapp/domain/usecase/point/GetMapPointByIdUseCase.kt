package pl.kamilszustak.hulapp.domain.usecase.point

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.domain.model.point.MapPoint

interface GetMapPointByIdUseCase {
    operator fun invoke(id: Long, shouldFetch: Boolean = true): Flow<Resource<MapPoint>>
}