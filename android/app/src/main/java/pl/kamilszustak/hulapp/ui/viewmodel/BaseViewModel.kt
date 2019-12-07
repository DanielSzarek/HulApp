package pl.kamilszustak.hulapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.kamilszustak.hulapp.application.BaseApplication
import pl.kamilszustak.hulapp.di.component.ApplicationComponent
import pl.kamilszustak.hulapp.util.isInternetConnected

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    init {
        getApplicationComponent().inject(this)
    }

    protected fun isInternetConnected(): Boolean =
        getApplication<BaseApplication>().isInternetConnected()

    protected fun AndroidViewModel.getApplicationComponent(): ApplicationComponent =
        getApplication<BaseApplication>().applicationComponent
}