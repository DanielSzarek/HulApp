package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import pl.kamilszustak.hulapp.common.data.Resource

class ResourceLiveData<T>(
    block: () -> LiveData<Resource<T>> = { MutableLiveData<Resource<T>>() }
) : RefreshableLiveData<Resource<T>>(block) {

    val dataLiveData: LiveData<T> = Transformations.map(this) {
        it.data
    }

    val loadingLiveData: LiveData<Boolean> = ResourceLoadingLiveData(this)

    val errorLiveData: SingleLiveEvent<String> = ResourceErrorLiveData(this)
}