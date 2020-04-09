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
    protected val _actionCompleted: SingleLiveData<Unit> = SingleLiveData()
    val actionCompleted: LiveData<Unit> = _actionCompleted

    protected val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _error: SingleLiveData<Int> = SingleLiveData()
    val error: LiveData<Int> = _error

    protected fun performAction(
        @StringRes errorMessageResource: Int,
        action: suspend () -> Result<Any>
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            action().onSuccess {
                _actionCompleted.call()
            }.onFailure {
                _error.value = errorMessageResource
            }

            _isLoading.value = false
        }
    }
}