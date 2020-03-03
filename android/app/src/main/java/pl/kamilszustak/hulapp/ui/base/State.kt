package pl.kamilszustak.hulapp.ui.base

import androidx.annotation.StringRes

sealed class State {
    object Loading : State()
    data class Error(@StringRes val messageResource: Int) : State()
    object Completed : State()
}