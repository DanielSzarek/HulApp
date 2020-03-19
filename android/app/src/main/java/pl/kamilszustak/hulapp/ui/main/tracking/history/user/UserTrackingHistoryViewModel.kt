package pl.kamilszustak.hulapp.ui.main.tracking.history.user

import android.app.Application
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.main.tracking.history.BaseTrackingHistoryViewModel
import javax.inject.Inject

class UserTrackingHistoryViewModel @Inject constructor(
    application: Application,
    trackRepository: TrackRepository
) : BaseTrackingHistoryViewModel(application, trackRepository) {

    fun loadData(userId: Long) {
        tracksResource.setFlowSource {
            trackRepository.getAllByUserId(userId)
        }
    }
}