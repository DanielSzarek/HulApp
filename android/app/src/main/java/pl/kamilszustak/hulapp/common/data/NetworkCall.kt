package pl.kamilszustak.hulapp.common.data

import pl.kamilszustak.hulapp.util.withIoContext
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

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
            Timber.i("1")
            val response = makeCall()
            Timber.i("2")

            if (response.isSuccessful) {
                Timber.i("success")

                onResponseSuccess()
                Result.success(Unit)
            } else {
                Timber.i("failure")
                val exception = HttpException(response)
                Result.failure(exception)
            }
        } catch (throwable: Throwable) {
            Timber.i("3")
            Timber.e(throwable)
            Result.failure(throwable)
        }
    }

    abstract suspend fun makeCall(): Response<ResponseType>

    abstract suspend fun mapResponse(response: ResponseType): ReturnType

    open suspend fun saveCallResult(result: ResponseType): Unit = Unit

    open suspend fun onResponseSuccess(): Unit = Unit
}