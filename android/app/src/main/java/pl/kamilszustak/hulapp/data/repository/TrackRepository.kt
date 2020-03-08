package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.TrackDao
import pl.kamilszustak.hulapp.data.mapper.TrackMapper
import pl.kamilszustak.hulapp.data.model.Track
import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.data.model.track.TrackJson
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackRepository @Inject constructor(
    private val trackDao: TrackDao,
    private val apiService: ApiService,
    private val userDetailsRepository: UserDetailsRepository
) {
    fun getAllOfCurrentUser(shouldFetch: Boolean = true): Flow<Resource<List<TrackEntity>>> {
        val userId = userDetailsRepository.getValue<Long>(UserDetailsRepository.UserDetailsKey.USER_ID)
        val mapper = TrackMapper(userId)

        return object : NetworkBoundResource<List<TrackJson>, List<TrackEntity>>() {
            override fun loadFromDatabase(): Flow<List<TrackEntity>> =
                trackDao.getAllByUserId(userId)

            override fun shouldFetch(data: List<TrackEntity>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<TrackJson>> =
                apiService.getCurrentUserTracks()

            override suspend fun saveFetchResult(result: List<TrackJson>) {
                val tracks = result.map { json ->
                    mapper.map(json)
                }

                trackDao.replaceAllByUserId(userId, tracks)
            }
        }.asFlow()
    }

    fun getAllByUserId(userId: Long, shouldFetch: Boolean = true): Flow<Resource<List<TrackEntity>>> {
        val mapper = TrackMapper(userId)

        return object : NetworkBoundResource<List<TrackJson>, List<TrackEntity>>() {
            override fun loadFromDatabase(): Flow<List<TrackEntity>> =
                trackDao.getAllByUserId(userId)

            override fun shouldFetch(data: List<TrackEntity>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<TrackJson>> =
                apiService.getAllTracksByUserId(userId)

            override suspend fun saveFetchResult(result: List<TrackJson>) {
                val tracks = result.map { json ->
                    mapper.map(json)
                }

                trackDao.replaceAllByUserId(userId, tracks)
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
                TODO()
            }
        }
    }

    suspend fun save(track: Track): Result<Track> {
        return object : NetworkCall<Track, Track>() {
            override suspend fun makeCall(): Response<Track> =
                apiService.postTrack(track)

            override suspend fun mapResponse(response: Track): Track = response

            override suspend fun saveCallResult(result: Track) {
                trackDao.insert(result)
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