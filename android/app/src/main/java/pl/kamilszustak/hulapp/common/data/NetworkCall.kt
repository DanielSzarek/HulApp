package pl.kamilszustak.hulapp.common.data

import retrofit2.HttpException
import retrofit2.Response

abstract class NetworkCall<ResponseType, ReturnType> {

    suspend fun call(): Result<ReturnType> {
        return try {
            val response = makeCall()
            val body = response.body()

            return if (response.isSuccessful && body != null) {
                saveCallResult(body)
                Result.success(mapResponse(body))
            } else {
                val exception = HttpException(response)
                Result.failure(exception)
            }
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }

    abstract suspend fun makeCall(): Response<ResponseType>

    abstract suspend fun mapResponse(response: ResponseType): ReturnType

    open suspend fun saveCallResult(result: ResponseType): Unit = Unit
}