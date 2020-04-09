package pl.kamilszustak.hulapp.ui.base.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData

abstract class StateViewModel(application: Application) : BaseViewModel(application) {
    protected val _completed: SingleLiveData<Unit> = SingleLiveData()
    val completed: LiveData<Unit> = _completed

    protected val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _error: SingleLiveData<Int> = SingleLiveData()
    val error: LiveData<Int> = _error
}