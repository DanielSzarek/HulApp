package pl.kamilszustak.hulapp.ui.main.tracking.details

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.main.tracking.details.base.BaseTrackDetailsViewModel
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject

class TrackDetailsViewModel @Inject constructor(
    application: Application,
    trackRepository: TrackRepository
) : BaseTrackDetailsViewModel(application, trackRepository) {

    private val _sharedTrack: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharedTrack: LiveData<ShareEvent> = _sharedTrack

    fun onShareTrackButtonClick(trackId: Long) {
        val event = ShareEvent(
            "https://hulapp.com/track/$trackId",
            "Udostępniona trasa",
            "Udostępnij trasę"
        )

        _sharedTrack.value = event
    }

    fun onDeleteTrackButtonClick(trackId: Long) {
        performAction(R.string.track_deleting_error_message) {
            withIOContext {
                trackRepository.deleteById(trackId)
            }
        }
    }
}