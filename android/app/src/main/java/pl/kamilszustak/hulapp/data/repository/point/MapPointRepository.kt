package pl.kamilszustak.hulapp.data.repository.point

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.MapPointDao
import pl.kamilszustak.hulapp.data.database.dao.UserDao
import pl.kamilszustak.hulapp.domain.mapper.point.MapPointJsonMapper
import pl.kamilszustak.hulapp.domain.model.network.AddMapPointRequestBody
import pl.kamilszustak.hulapp.domain.model.point.MapPointJson
import pl.kamilszustak.hulapp.domain.model.point.MapPointWithAuthor
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapPointRepository @Inject constructor(
    private val mapPointDao: MapPointDao,
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val mapPointJsonMapper: MapPointJsonMapper
) {
    fun getAll(shouldFetch: Boolean): Flow<Resource<List<MapPointWithAuthor>>> {
        return object : NetworkBoundResource<List<MapPointJson>, List<MapPointWithAuthor>>() {
            override fun loadFromDatabase(): Flow<List<MapPointWithAuthor>> =
                mapPointDao.getAllWithAuthors()

            override fun shouldFetch(data: List<MapPointWithAuthor>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<MapPointJson>> =
                apiService.getAllMapPoints()

            override suspend fun saveFetchResult(result: List<MapPointJson>) {
                val users = result.map(MapPointJson::author)
                userDao.insertAll(users)

                mapPointJsonMapper.onMapAll(result) { points ->
                    mapPointDao.replaceAll(points)
                }
            }
        }.asFlow()
    }

    fun getById(id: Long, shouldFetch: Boolean): Flow<Resource<MapPointWithAuthor>> {
        return object : NetworkBoundResource<MapPointJson, MapPointWithAuthor>() {
            override fun loadFromDatabase(): Flow<MapPointWithAuthor> =
                mapPointDao.getByIdWithAuthor(id)

            override fun shouldFetch(data: MapPointWithAuthor?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<MapPointJson> =
                apiService.getMapPointById(id)

            override suspend fun saveFetchResult(result: MapPointJson) {
                userDao.insert(result.author)
                mapPointJsonMapper.onMap(result) { point ->
                    mapPointDao.insert(point)
                }
            }
        }.asFlow()
    }

    suspend fun add(requestBody: AddMapPointRequestBody): Result<Unit> {
        return object : NetworkCall<MapPointJson, Unit>() {
            override suspend fun makeCall(): Response<MapPointJson> =
                apiService.addMapPoint(requestBody)

            override suspend fun mapResponse(response: MapPointJson) = Unit

            override suspend fun saveCallResult(result: MapPointJson) {
                mapPointJsonMapper.onMap(result) { point ->
                    mapPointDao.insert(point)
                }
            }
        }.callForResponse()
    }

    suspend fun deleteById(id: Long): Result<Unit> {
        return object : NetworkCall<MapPointJson, Unit>() {
            override suspend fun makeCall(): Response<MapPointJson> =
                apiService.deleteMapPointById(id)

            override suspend fun mapResponse(response: MapPointJson) = Unit

            override suspend fun onResponseSuccess() {
                mapPointDao.deleteById(id)
            }
        }.call()
    }
}