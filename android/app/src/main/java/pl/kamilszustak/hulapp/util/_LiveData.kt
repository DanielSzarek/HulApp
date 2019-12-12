package pl.kamilszustak.hulapp.util

import androidx.lifecycle.*
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.common.livedata.ResourceLiveData


inline fun <S, T> LiveData<T>.mapNotNull(crossinline function: (T) -> S?): LiveData<S> {
    val result = MediatorLiveData<S>()
    result.addSource(this) {
        val data = function(it)
        if (data != null)
            result.value = data
    }

    return result
}

inline fun <T> LiveData<T>.filter(crossinline function: (T?) -> Boolean): LiveData<T> {
    val result = MediatorLiveData<T>()
    result.addSource(this) {
        if (function(it))
            result.value = it
    }

    return result
}

inline fun <T> LiveData<T>.filterNotNull(crossinline function: (T) -> Boolean): LiveData<T> {
    val result = MediatorLiveData<T>()
    result.addSource(this) {
        if (it != null && function(it))
            result.value = it
    }
    return result
}

fun <T> LiveData<T>.merge(source: LiveData<T>): LiveData<T> {
    val result = MediatorLiveData<T>()
    result.addSource(this) {
        result.value = it
    }
    result.addSource(source) {
        result.value = it
    }

    return result
}

//inline fun <S, T> LiveData<S>.switchMapResource(crossinline function: (S) -> ResourceLiveData<T>): ResourceLiveData<T> {
//
//}