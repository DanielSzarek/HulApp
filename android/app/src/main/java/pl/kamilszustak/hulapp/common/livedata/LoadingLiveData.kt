package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

open class LoadingLiveData<T>(
    source: LiveData<T>,
    condition: (T) -> Boolean
) : MediatorLiveData<Boolean>() {

    init {
        this.addSource(source) {
            this.value = condition(it)
        }
    }
}