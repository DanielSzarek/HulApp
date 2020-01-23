package pl.kamilszustak.hulapp.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kamilszustak.hulapp.common.data.NetworkBoundResource
import pl.kamilszustak.hulapp.common.data.NetworkCall
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.data.database.dao.TrackDao
import pl.kamilszustak.hulapp.data.model.Track
import pl.kamilszustak.hulapp.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackRepository @Inject constructor(
    private val trackDao: TrackDao,
    private val apiService: ApiService
) {

    fun getAll(shouldFetch: Boolean = true): Flow<Resource<List<Track>>> {
        return object : NetworkBoundResource<List<Track>, List<Track>>() {
            override fun loadFromDatabase(): Flow<List<Track>> =
                trackDao.getAll()

            override fun shouldFetch(data: List<Track>?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<List<Track>> =
                apiService.getAllTracks()

            override suspend fun saveFetchResult(result: List<Track>) {
                trackDao.insertAll(result)
            }
        }.asFlow()
    }

    fun getById(id: Long, shouldFetch: Boolean): Flow<Resource<Track>> {
        return object : NetworkBoundResource<Track, Track>() {
            override fun loadFromDatabase(): Flow<Track> =
                trackDao.getById(id)

            override fun shouldFetch(data: Track?): Boolean = shouldFetch

            override suspend fun fetchFromNetwork(): Response<Track> =
                apiService.getTrackById(id)

            override suspend fun saveFetchResult(result: Track) {
                trackDao.insert(result)
            }
        }.asFlow()
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
}