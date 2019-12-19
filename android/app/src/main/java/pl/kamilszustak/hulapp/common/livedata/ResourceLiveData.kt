package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.util.mapNotNull

class ResourceLiveData<T>(
    block: () -> LiveData<Resource<T>> = { MutableLiveData<Resource<T>>() }
) : RefreshableLiveData<Resource<T>>(block) {

    val data: LiveData<T> = this.mapNotNull {
        it.data
    }

    val isLoading: LiveData<Boolean> = ResourceLoadingLiveData(this)

    val error: SingleLiveEvent<String> = ResourceErrorLiveData(this)
}