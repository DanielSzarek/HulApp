package pl.kamilszustak.hulapp.common.data

import pl.kamilszustak.hulapp.common.data.Status.*

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    val isSuccess: Boolean
        get() = (this.status == Status.SUCCESS)

    val isError: Boolean
        get() = (this.status == Status.ERROR)

    val isLoading: Boolean
        get() = (this.status == Status.LOADING)

    inline fun <R> mapData(transform: (T) -> R): Resource<R> {
        val data = this.data
        val mappedData = if (data != null) {
            transform(data)
        } else {
            data
        }

        return when {
            this.isSuccess -> success(mappedData)
            this.isLoading -> loading(mappedData)
            this.isError -> error(
                this.message ?: "Unknown Resource error",
                mappedData
            )
            else -> throw IllegalStateException("Illegal Resource status: ${this.status}")
        }
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}