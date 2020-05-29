package pl.kamilszustak.hulapp.ui.base.viewmodel

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData

abstract class StateViewModel(application: Application) : BaseViewModel(application) {
    protected val _actionCompletedEvent: SingleLiveData<Unit> = SingleLiveData()
    val actionCompletedEvent: LiveData<Unit> = _actionCompletedEvent

    protected val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _errorEvent: SingleLiveData<Int> = SingleLiveData()
    val errorEvent: LiveData<Int> = _errorEvent

    protected fun performAction(
        @StringRes errorMessageResource: Int,
        action: suspend () -> Result<Any>
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            action().onSuccess {
                _actionCompletedEvent.call()
            }.onFailure {
                _errorEvent.value = errorMessageResource
            }

            _isLoading.value = false
        }
    }
}