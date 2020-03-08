package pl.kamilszustak.hulapp.ui.main.tracking.details

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.mapNotNull
import javax.inject.Inject

class TrackDetailsViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository
) : BaseViewModel(application) {

    val trackResource: ResourceDataSource<TrackEntity> = ResourceDataSource()

    val exhaustEmission: LiveData<Double> = trackResource.data.mapNotNull { track ->
        120 * track.distance
    }

    private val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: SingleLiveData<String> = SingleLiveData()
    val error: LiveData<String> = _error

    private val _deletingCompleted: SingleLiveData<Unit> = SingleLiveData()
    val deletingCompleted: LiveData<Unit> = _deletingCompleted

    private val _sharedTrackIntent: SingleLiveData<ShareEvent> = SingleLiveData()
    val sharedTrackIntent: LiveData<ShareEvent> = _sharedTrackIntent

    fun loadTrack(trackId: Long, force: Boolean = false) {
        initialize(force) {
            trackResource.changeFlowSource {
                trackRepository.getById(trackId)
            }
        }
    }

    fun onShareTrackButtonClick() {
        val track = trackResource.data.value
        if (track == null) {
            _error.value = "Nie załadowano trasy"
            return
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            this.putExtra(Intent.EXTRA_TEXT, "test")
            this.type = "text/plain"
        }

        _sharedTrackIntent.value = ShareEvent(intent, "Udostępnij trasę")
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