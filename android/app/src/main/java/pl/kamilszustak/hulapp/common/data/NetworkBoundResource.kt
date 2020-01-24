package pl.kamilszustak.hulapp.common.data

import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

abstract class NetworkBoundResource<ResponseType, EntityType> {

    fun asFlow(): Flow<Resource<EntityType>> {
        return flow {
            emit(Resource.loading(null))
            val databaseValue = loadFromDatabase().first()

            if (shouldFetch(databaseValue)) {
                emit(Resource.loading(databaseValue))

                try {
                    val response = fetchFromNetwork()
                    val body = response.body()

                    if (response.isSuccessful && body != null) {
                        saveFetchResult(body)
                    } else {
                        throw HttpException(response)
                    }

                    emitAll(loadFromDatabase().map {
                        Resource.success(it)
                    })
                } catch (throwable: Throwable) {
                    onFetchFailed(throwable)
                    emitAll(loadFromDatabase().map {
                        Resource.error(throwable.message ?: "", it)
                    })
                }
            } else {
                emitAll(loadFromDatabase().map {
                    Resource.success(it)
                })
            }
        }
    }

    abstract fun loadFromDatabase(): Flow<EntityType>

    abstract suspend fun fetchFromNetwork(): Response<ResponseType>

    abstract suspend fun saveFetchResult(result: ResponseType)

    open fun onFetchFailed(throwable: Throwable): Unit = Unit

    open fun shouldFetch(data: EntityType?): Boolean = true
}