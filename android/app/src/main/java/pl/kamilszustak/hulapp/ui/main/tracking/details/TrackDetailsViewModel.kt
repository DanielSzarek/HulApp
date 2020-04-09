package pl.kamilszustak.hulapp.ui.main.tracking.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private val _deletingCompleted: SingleLiveData<Unit> = SingleLiveData()
    val deletingCompleted: LiveData<Unit> = _deletingCompleted

    private val _sharedTrack: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharedTrack: LiveData<ShareEvent> = _sharedTrack

    fun onShareTrackButtonClick(trackId: Long) {
        val event = ShareEvent(
            "http://hulapp.com/track/$trackId",
            "Udostępniona trasa",
            "Udostępnij trasę"
        )

        _sharedTrack.value = event
    }

    fun onDeleteTrackButtonClick(trackId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            val result = withIOContext {
                trackRepository.deleteById(trackId)
            }
            result.onSuccess {
                _deletingCompleted.call()
            }.onFailure {
                _error.value = R.string.track_deleting_error_message
            }

            _isLoading.value = false
        }
    }
}