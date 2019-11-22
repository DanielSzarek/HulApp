package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.common.data.Resource
import pl.kamilszustak.hulapp.common.data.Status

class ResourceErrorLiveData<T>(
    source: LiveData<Resource<T>>
) : ErrorLiveData<Resource<T>>(source, source.value?.message ?: "", {
    it.status == Status.ERROR
})