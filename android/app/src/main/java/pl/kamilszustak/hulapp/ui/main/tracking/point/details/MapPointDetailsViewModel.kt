package pl.kamilszustak.hulapp.ui.main.tracking.point.details

import android.app.Application
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.domain.model.point.MapPoint
import pl.kamilszustak.hulapp.domain.usecase.point.DeleteMapPointByIdUseCase
import pl.kamilszustak.hulapp.domain.usecase.point.GetMapPointByIdUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class MapPointDetailsViewModel @Inject constructor(
    application: Application,
    private val getMapPointById: GetMapPointByIdUseCase,
    private val deleteMapPointById: DeleteMapPointByIdUseCase
) : StateViewModel(application) {

    val mapPointResource: ResourceDataSource<MapPoint> = ResourceDataSource()

    fun loadData(mapPointId: Long) {
        initialize {
            mapPointResource.setFlowSource {
                getMapPointById(mapPointId)
            }
        }
    }

    fun onRefresh() {
        mapPointResource.refresh()
    }

    fun onDeleteButtonClick(mapPointId: Long) {
        performAction(R.string.deleting_map_point_error_message) {
            deleteMapPointById(mapPointId)
        }
    }
}