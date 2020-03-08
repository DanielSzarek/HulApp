package pl.kamilszustak.hulapp.ui.main.tracking.history

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import javax.inject.Inject

class TrackingHistoryViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository
) : BaseViewModel(application) {

    val tracksResource: ResourceDataSource<List<TrackEntity>> = ResourceDataSource()

    init {
        tracksResource.changeFlowSource {
            trackRepository.getAllOfCurrentUser()
        }
    }
}