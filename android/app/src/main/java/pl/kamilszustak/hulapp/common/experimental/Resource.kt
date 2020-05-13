package pl.kamilszustak.hulapp.common.experimental

sealed class Resource<out T> {
    data class Success<out T>(
        val data: T?
    ) : Resource<T>()

    data class Loading<out T>(
        val data: T?
    ) : Resource<T>()

    data class Error<out T>(
        val data: T?,
        val exception: Exception
    ) : Resource<T>()

    val isSuccess: Boolean
        get() = (this is Success)

    val isLoading: Boolean
        get() = (this is Loading)

    val isError: Boolean
        get() = (this is Error)

    fun <R> mapData(transform: (T) -> R): Resource<R> {
        return when (this) {
            is Success -> {
                val mappedData = mapInternal(this.data, transform)
                Success(mappedData)
            }
            is Loading -> {
                val mappedData = mapInternal(this.data, transform)
                Loading(mappedData)
            }
            is Error -> {
                val mappedData = mapInternal(this.data, transform)
                Error(mappedData, this.exception)
            }
        }
    }

    private fun <R> mapInternal(data: T?, transform: (T) -> R): R? =
        if (data != null) {
            transform(data)
        } else {
            null
        }
}