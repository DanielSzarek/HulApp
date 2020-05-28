package pl.kamilszustak.hulapp.util

import androidx.lifecycle.*

inline fun <S, T> LiveData<T>.mapNotNull(crossinline function: (T) -> S?): LiveData<S> {
    val result = MediatorLiveData<S>()
    result.addSource(this) {
        val data = function(it)
        if (data != null) {
            result.value = data
        }
    }

    return result
}

inline fun <S, T> LiveData<T>.mapUnique(crossinline function: (T) -> S?): LiveData<S> {
    val result = MediatorLiveData<S>()
    result.addSource(this) {
        val data = function(it)
        if (data != result.value) {
            result.value = data
        }
    }

    return result
}

inline fun <S, T> LiveData<T>.mapUniqueNotNull(crossinline function: (T) -> S?): LiveData<S> {
    val result = MediatorLiveData<S>()
    result.addSource(this) {
        val data = function(it)
        if (data != null && data != result.value) {
            result.value = data
        }
    }

    return result
}

inline fun <T> LiveData<T>.filter(crossinline function: (T?) -> Boolean): LiveData<T> {
    val result = MediatorLiveData<T>()
    result.addSource(this) {
        if (function(it)) {
            result.value = it
        }
    }

    return result
}

inline fun <T> LiveData<T>.filterNotNull(crossinline function: (T) -> Boolean): LiveData<T> {
    val result = MediatorLiveData<T>()
    result.addSource(this) {
        if (it != null && function(it)) {
            result.value = it
        }
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

fun <T> LiveData<T>.isValueEqual(source: LiveData<T>): LiveData<Boolean> {
    val result = MediatorLiveData<Boolean>()
    val sources = listOf(this, source)

    result.addSources(sources) {
        result.value = (this.value == source.value)
    }

    return result
}

fun <S, T> MediatorLiveData<T>.addSources(
    sources: List<LiveData<S>>, observer: Observer<in S>
) {
    sources.forEach { source ->
        this.addSource(source) { value ->
            observer.onChanged(value)
        }
    }
}

inline fun <S, T> MediatorLiveData<T>.addSources(
    sources: List<LiveData<S>>,
    crossinline onChanged: (S) -> Unit
) {
    sources.forEach { source ->
        this.addSource(source) { value ->
            onChanged(value)
        }
    }
}

fun <S, T> MediatorLiveData<T>.addSources(
    observer: Observer<in S>,
    vararg sources: LiveData<S>
) {
    sources.forEach { source ->
        this.addSource(source) { value ->
            observer.onChanged(value)
        }
    }
}

inline fun <S, T> MediatorLiveData<T>.addSources(
    crossinline onChanged: (S) -> Unit,
    vararg sources: LiveData<S>
) {
    sources.forEach { source ->
        this.addSource(source) { value ->
            onChanged(value)
        }
    }
}

inline fun <T> MutableLiveData<T>.update(transform: (T?) -> T?) {
    val updated = transform(this.value)
    this.value = updated
}

inline fun <T> MutableLiveData<T>.updateNotNull(transform: (T) -> T?) {
    val value = this.value
    if (value != null) {
        val updated = transform(value)
        this.value = updated
    }
}