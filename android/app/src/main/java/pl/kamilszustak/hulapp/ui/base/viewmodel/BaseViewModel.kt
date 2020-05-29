package pl.kamilszustak.hulapp.ui.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.kamilszustak.hulapp.application.BaseApplication
import pl.kamilszustak.hulapp.util.isInternetConnected

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private var _isInitialized = false
    protected val isInitialized = _isInitialized

    protected fun initialize(force: Boolean = false, action: () -> Unit) {
        if (_isInitialized && !force) {
            return
        }

        action()
        _isInitialized = true
    }

    protected fun isInternetConnected(): Boolean =
        getApplication<BaseApplication>().isInternetConnected()
}