package pl.kamilszustak.hulapp.ui.main.tracking.history.main

import android.app.Application
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.main.tracking.history.BaseTrackingHistoryViewModel
import javax.inject.Inject

class TrackingHistoryViewModel @Inject constructor(
    application: Application,
    trackRepository: TrackRepository
) : BaseTrackingHistoryViewModel(application, trackRepository) {

    init {
        tracksResource.setFlowSource {
            trackRepository.getAllOfCurrentUser()
        }
    }
}