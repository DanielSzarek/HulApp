package pl.kamilszustak.hulapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.kamilszustak.hulapp.application.BaseApplication
import pl.kamilszustak.hulapp.util.isInternetConnected

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected fun isInternetConnected(): Boolean =
        getApplication<BaseApplication>().isInternetConnected()
}