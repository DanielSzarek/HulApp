package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.TrackDao
import pl.kamilszustak.hulapp.domain.mapper.track.TrackMapper
import pl.kamilszustak.hulapp.domain.model.Track
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity
import pl.kamilszustak.hulapp.domain.model.track.TrackJson
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackRepository @Inject constructor(
    private val trackDao: TrackDao,
    private val apiService: ApiService,
    private val userDetailsRepository: UserDetailsRepository,
    private val trackMapper: TrackMapper
) {
    fun getAllOfCurrentUser(
        limit: Int = Int.MAX_VALUE,
        shouldFetch: Boolean = true
    ): Flow<Resource<List<TrackEntity>>> {
        val userId = userDetailsRepository.getValue<Long>(UserDetailsRepository.UserDetailsKey.USER_ID)

        return object : NetworkBoundResource<List<TrackJson>, List<TrackEntity>>() {
            override fun loadFromDatabase(): Flow<List<TrackEntity>> =
                trackDao.getAllByUserId(userId, limit)

            override fun shouldFetch(data: List<TrackEntity>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<TrackJson>> =
                apiService.getCurrentUserTracks()

            override suspend fun saveFetchResult(result: List<TrackJson>) {
                trackMapper.onMapAll(result) { tracks ->
                    trackDao.replaceAllByUserId(userId, tracks)
                }
            }
        }.asFlow()
    }

    fun getAllByUserId(
        userId: Long,
        limit: Int = Int.MAX_VALUE,
        shouldFetch: Boolean = true
    ): Flow<Resource<List<TrackEntity>>> {
        return object : NetworkBoundResource<List<TrackJson>, List<TrackEntity>>() {
            override fun loadFromDatabase(): Flow<List<TrackEntity>> =
                trackDao.getAllByUserId(userId, limit)

            override fun shouldFetch(data: List<TrackEntity>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<TrackJson>> =
                apiService.getAllTracksByUserId(userId)

            override suspend fun saveFetchResult(result: List<TrackJson>) {
                trackMapper.onMapAll(result) { tracks ->
                    trackDao.replaceAllByUserId(userId, tracks)
                }
            }
        }.asFlow()
    }

    fun getById(id: Long, shouldFetch: Boolean = true): Flow<Resource<TrackEntity>> {
        return object : NetworkBoundResource<TrackJson, TrackEntity>() {
            override fun loadFromDatabase(): Flow<TrackEntity> =
                trackDao.getById(id)

            override fun shouldFetch(data: TrackEntity?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<TrackJson> =
                apiService.getTrackById(id)

            override suspend fun saveFetchResult(result: TrackJson) {
                trackMapper.onMap(result) { track ->
                    trackDao.insert(track)
                }
            }
        }.asFlow()
    }

    suspend fun save(track: Track): Result<TrackEntity> {
        return object : NetworkCall<TrackJson, TrackEntity>() {
            override suspend fun makeCall(): Response<TrackJson> =
                apiService.postTrack(track)

            override suspend fun mapResponse(response: TrackJson): TrackEntity =
                trackMapper.map(response)

            override suspend fun saveCallResult(result: TrackJson) {
                trackMapper.onMap(result) { track ->
                    trackDao.insert(track)
                }
            }
        }.callForResponse()
    }

    suspend fun deleteById(id: Long): Result<Unit> {
        return object : NetworkCall<Unit, Unit>() {
            override suspend fun makeCall(): Response<Unit> =
                apiService.deleteTrackById(id)

            override suspend fun mapResponse(response: Unit) = Unit

            override suspend fun onResponseSuccess() {
                trackDao.deleteById(id)
            }
        }.call()
    }
}