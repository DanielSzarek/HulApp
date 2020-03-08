package pl.kamilszustak.hulapp.ui.main.tracking.details.base

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.mapNotNull

abstract class BaseTrackDetailsViewModel(
    application: Application,
    protected val trackRepository: TrackRepository
) : BaseViewModel(application) {

    val trackResource: ResourceDataSource<TrackEntity> = ResourceDataSource()

    val exhaustEmission: LiveData<Double> = trackResource.data.mapNotNull { track ->
        120 * track.distance
    }

    fun loadData(trackId: Long) {
        initialize {
            trackResource.changeFlowSource {
                trackRepository.getById(trackId)
            }
        }
    }

    fun onRefresh() {
        trackResource.refresh()
    }
}