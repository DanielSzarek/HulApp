package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.common.data.Status

class ResourceLoadingLiveData<T>(
    source: LiveData<Resource<T>>
) : LoadingLiveData<Resource<T>>(source, {
    it.status == Status.LOADING && it.data == null
})