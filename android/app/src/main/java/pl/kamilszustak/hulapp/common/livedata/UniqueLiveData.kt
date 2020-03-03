package pl.kamilszustak.hulapp.common.livedata

import androidx.lifecycle.MutableLiveData

class UniqueLiveData<T> : MutableLiveData<T> {

    constructor() : super()

    constructor(value: T) : super(value)

    override fun setValue(value: T?) {
        if (this.value != value) {
            super.setValue(value)
        }
    }

    override fun postValue(value: T?) {
        if (this.value != value) {
            super.postValue(value)
        }
    }
}