package pl.kamilszustak.hulapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.kamilszustak.hulapp.application.BaseApplication
import pl.kamilszustak.hulapp.util.isInternetConnected

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private var _isInitialized = false
    protected val isInitialized = _isInitialized

    fun initialize(force: Boolean = false, function: () -> Unit) {
        if (_isInitialized && !force) {
            return
        }

        function()
        _isInitialized = true
    }

    protected fun isInternetConnected(): Boolean =
        getApplication<BaseApplication>().isInternetConnected()
}