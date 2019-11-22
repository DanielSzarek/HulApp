package pl.kamilszustak.hulapp.common.data

import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundResource<T> {

    fun asFlow(): Flow<Resource<T>> {
        return flow {
            emit(Resource.loading(null))
            val databaseValue = loadFromDatabase().first()

            if (shouldFetch(databaseValue)) {
                emit(Resource.loading(databaseValue))

                try {
                    val response = fetchFromNetwork()
                    val body = response.body()

                    if (response.isSuccessful && body != null)
                        saveFetchResult(body)

                    emitAll(loadFromDatabase().map {
                        Resource.success(it)
                    })
                } catch(throwable: Throwable) {
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

    abstract fun loadFromDatabase(): Flow<T>

    abstract suspend fun fetchFromNetwork(): Response<T>

    abstract suspend fun saveFetchResult(data: T)

    open fun onFetchFailed(throwable: Throwable) = Unit

    open fun shouldFetch(data: T) = true
}