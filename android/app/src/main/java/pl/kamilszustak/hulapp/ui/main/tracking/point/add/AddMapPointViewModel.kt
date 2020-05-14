package pl.kamilszustak.hulapp.ui.main.tracking.point.add

import android.app.Application
import com.google.android.gms.maps.model.LatLng
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class AddMapPointViewModel @Inject constructor(
    application: Application
) : StateViewModel(application) {

    val pointName: UniqueLiveData<String> = UniqueLiveData()
    val pointDescription: UniqueLiveData<String> = UniqueLiveData()

    fun onAddButtonClick(latLng: LatLng) {

    }
}