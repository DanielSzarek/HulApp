package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import pl.kamilszustak.hulapp.common.data.Resource

class ResourceLiveData<T>(
    block: () -> LiveData<Resource<T>> = { MutableLiveData<Resource<T>>() }
) : RefreshableLiveData<Resource<T>>(block) {

    val dataLiveData: LiveData<T> = map {
        it.data
    }

    val loadingLiveData: LiveData<Boolean> = ResourceLoadingLiveData(this)

    val errorLiveData: SingleLiveEvent<String> = ResourceErrorLiveData(this)

    private inline fun <S> map(crossinline block: (Resource<T>) -> S?): LiveData<S> {
        val result = MediatorLiveData<S>()
        result.addSource(this) {
            val data = block(it)
            if (data != null)
                result.value = data
        }

        return result
    }
}