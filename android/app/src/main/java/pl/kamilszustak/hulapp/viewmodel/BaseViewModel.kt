package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.kamilszustak.hulapp.util.getApplicationComponent

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    init {
        getApplicationComponent().inject(this)
    }
}