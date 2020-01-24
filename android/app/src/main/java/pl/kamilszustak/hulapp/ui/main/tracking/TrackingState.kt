package pl.kamilszustak.hulapp.ui.main.tracking

import pl.kamilszustak.hulapp.data.model.Track

sealed class TrackingState {

    val isIdle: Boolean =
        this is Idle

    val isStarted: Boolean =
        this is Started

    val isPaused: Boolean =
        this is Paused

    val isEnded: Boolean =
        this is Ended

    object Idle : TrackingState()
    object Started : TrackingState()
    object Paused : TrackingState()
    object Ended : TrackingState()
}