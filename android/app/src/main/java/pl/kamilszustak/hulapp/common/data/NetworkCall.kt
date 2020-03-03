package pl.kamilszustak.hulapp.common.data

import retrofit2.HttpException
import retrofit2.Response

abstract class NetworkCall<ResponseType, ReturnType> {

    suspend fun callForResponse(): Result<ReturnType> {
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

    suspend fun call(): Result<Unit> {
        return try {
            val response = makeCall()
            if (response.isSuccessful) {
                onResponseSuccess()
                Result.success(Unit)
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

    open suspend fun onResponseSuccess(): Unit = Unit
}