package pl.kamilszustak.hulapp.util

import androidx.lifecycle.AndroidViewModel
import pl.kamilszustak.hulapp.application.BaseApplication
import pl.kamilszustak.hulapp.di.component.ApplicationComponent

fun AndroidViewModel.getApplicationComponent(): ApplicationComponent =
    getApplication<BaseApplication>().applicationComponent

fun AndroidViewModel.isInternetConnected(): Boolean =
    getApplication<BaseApplication>().isInternetConnected()