package pl.kamilszustak.hulapp.ui.main.tracking

sealed class TrackingState {
    object Started : TrackingState()
    object Paused : TrackingState()
    class Stopped : TrackingState()
}