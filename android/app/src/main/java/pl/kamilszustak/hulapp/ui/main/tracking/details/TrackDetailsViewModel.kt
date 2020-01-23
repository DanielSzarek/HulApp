package pl.kamilszustak.hulapp.ui.main.tracking.details

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.data.model.Track
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class TrackDetailsViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository
) : BaseViewModel(application) {

    val trackResource: ResourceDataSource<Track> = ResourceDataSource()

    fun loadTrack(trackId: Long, force: Boolean = false) {
        initialize(force) {
            trackResource.changeFlowSource {
                trackRepository.getById(trackId)
            }
        }
    }
}