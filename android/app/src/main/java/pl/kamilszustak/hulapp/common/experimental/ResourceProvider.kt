package pl.kamilszustak.hulapp.common.experimental

import android.util.Log
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

abstract class ResourceProvider<ResponseType, EntityType> {

    fun asFlow(): Flow<Resource<EntityType>> = flow {
        emit(Resource.Loading(null))
        val databaseValue = loadFromDatabase().first()

        if (shouldFetch(databaseValue)) {
            emit(Resource.Loading(databaseValue))

            try {
                val response = fetchFromNetwork()
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    saveFetchResult(body)
                } else {
                    throw HttpException(response)
                }

                emitAll(loadFromDatabase().map {
                    Resource.Success(it)
                })
            } catch (throwable: Throwable) {
                onFetchFailed(throwable)
                emitAll(loadFromDatabase().map {
                    Resource.Error(it, throwable as java.lang.Exception)
                })
            }
        } else {
            emitAll(loadFromDatabase().map {
                Resource.Success(it)
            })
        }
    }

    abstract fun loadFromDatabase(): Flow<EntityType>

    open fun shouldFetch(data: EntityType?): Boolean = true

    abstract suspend fun fetchFromNetwork(): Response<ResponseType>

    open fun onFetchFailed(throwable: Throwable) {
        Log.e("ResourceProvider", throwable.message, throwable)
    }

    abstract suspend fun saveFetchResult(result: ResponseType)
}