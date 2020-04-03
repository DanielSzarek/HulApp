package pl.kamilszustak.hulapp.ui.main.tracking.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.main.tracking.details.base.BaseTrackDetailsViewModel
import javax.inject.Inject

class TrackDetailsViewModel @Inject constructor(
    application: Application,
    trackRepository: TrackRepository
) : BaseTrackDetailsViewModel(application, trackRepository) {

    private val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: SingleLiveData<String> = SingleLiveData()
    val error: LiveData<String> = _error

    private val _deletingCompleted: SingleLiveData<Unit> = SingleLiveData()
    val deletingCompleted: LiveData<Unit> = _deletingCompleted

    private val _sharedTrack: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharedTrack: LiveData<ShareEvent> = _sharedTrack

    fun onShareTrackButtonClick() {
        val track = trackResource.data.value
        if (track == null) {
            _error.value = "Nie załadowano trasy"
            return
        }

        val event = ShareEvent(
            "http://hulapp.com/track/${track.id}",
            "Udostępniona trasa",
            "Udostępnij trasę"
        )

        _sharedTrack.value = event
    }

    fun onDeleteTrackButtonClick() {
        val trackId = trackResource.data.value?.id
        if (trackId == null) {
            _error.value = "Nie załadowano trasy"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)

            val result = trackRepository.deleteById(trackId)
            result.onSuccess {
                _deletingCompleted.callAsync()
            }.onFailure {
                _error.postValue("Wystąpił błąd podczas usuwania trasy")
            }

            _isLoading.postValue(false)
        }
    }
}