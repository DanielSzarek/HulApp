package pl.kamilszustak.hulapp.ui.main.tracking.history

import android.app.Application
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.base.viewmodel.BaseViewModel

abstract class BaseTrackingHistoryViewModel(
    application: Application,
    protected val trackRepository: TrackRepository
) : BaseViewModel(application) {

    val tracksResource: ResourceDataSource<List<TrackEntity>> = ResourceDataSource()

    fun onRefresh() {
        tracksResource.refresh()
    }
}