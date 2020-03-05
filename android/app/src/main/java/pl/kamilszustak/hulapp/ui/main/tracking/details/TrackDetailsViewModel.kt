package pl.kamilszustak.hulapp.ui.main.tracking.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.Track
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.mapNotNull
import pl.kamilszustak.hulapp.util.withIOContext
import javax.inject.Inject

class TrackDetailsViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository
) : BaseViewModel(application) {

    val trackResource: ResourceDataSource<Track> = ResourceDataSource()

    val exhaustEmission: LiveData<Double> = trackResource.data.mapNotNull { track ->
        120 * track.distance
    }

    private val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: SingleLiveData<String> = SingleLiveData()
    val error: LiveData<String> = _error

    private val _deletingCompleted: SingleLiveData<Unit> = SingleLiveData()
    val deletingCompleted: LiveData<Unit> = _deletingCompleted

    fun loadTrack(trackId: Long, force: Boolean = false) {
        initialize(force) {
            trackResource.changeFlowSource {
                trackRepository.getById(trackId)
            }
        }
    }

    fun onDeleteTrackButtonClick() {
        val trackId = trackResource.data.value?.id
        if (trackId == null) {
            _error.value = "Nie załadowano trasy"
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.setValue(true)

            val result = withIOContext {
                trackRepository.deleteById(trackId)
            }

            if (result.isSuccess) {
                _deletingCompleted.call()
            } else {
                _error.value = "Wystąpił błąd podczas usuwania trasy"
            }

            _isLoading.setValue(false)
        }
    }
}