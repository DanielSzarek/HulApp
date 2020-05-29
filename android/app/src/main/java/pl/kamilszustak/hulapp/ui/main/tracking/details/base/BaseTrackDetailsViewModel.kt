package pl.kamilszustak.hulapp.ui.main.tracking.details.base

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import pl.kamilszustak.hulapp.util.mapNotNull
import pl.kamilszustak.hulapp.util.round

abstract class BaseTrackDetailsViewModel(
    application: Application,
    protected val trackRepository: TrackRepository
) : StateViewModel(application) {

    val trackResource: ResourceDataSource<TrackEntity> = ResourceDataSource()

    val exhaustEmission: LiveData<Double> = trackResource.data.mapNotNull { track ->
        (120 * track.distance).round()
    }

    fun loadData(trackId: Long) {
        initialize {
            trackResource.setFlowSource {
                trackRepository.getById(trackId)
            }
        }
    }

    fun onRefresh() {
        trackResource.refresh()
    }
}