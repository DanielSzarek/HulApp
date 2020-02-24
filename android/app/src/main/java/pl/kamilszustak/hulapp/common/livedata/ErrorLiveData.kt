package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

open class ErrorLiveData<T>(
    source: LiveData<T>,
    message: String,
    condition: (T) -> Boolean
) : SingleLiveData<String>() {

    private val updater: MediatorLiveData<T> = MediatorLiveData()

    init {
        updater.addSource(source) {
            if (condition(it))
                this.value = message
        }
    }
}