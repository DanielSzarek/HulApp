package pl.kamilszustak.hulapp.ui.main.tracking.point.add

import android.app.Application
import com.google.android.gms.maps.model.LatLng
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.domain.model.network.AddMapPointRequestBody
import pl.kamilszustak.hulapp.domain.usecase.point.AddMapPointUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import javax.inject.Inject

class AddMapPointViewModel @Inject constructor(
    application: Application,
    private val addMapPoint: AddMapPointUseCase
) : StateViewModel(application) {

    val pointName: UniqueLiveData<String> = UniqueLiveData()
    val pointDescription: UniqueLiveData<String> = UniqueLiveData()
    val pointRating: UniqueLiveData<Float> = UniqueLiveData()

    fun onRatingChanged(rating: Float) {
        pointRating.value = rating
    }

    fun onAddButtonClick(latLng: LatLng) {
        val name = pointName.value?.trim()
        val description = pointDescription.value?.trim()
        val rating = pointRating.value?.toInt()

        if (name.isNullOrBlank()) {
            _errorEvent.value = R.string.empty_map_point_name_error_message
            return
        }

        if (description.isNullOrBlank()) {
            _errorEvent.value = R.string.empty_map_point_description_error_message
            return
        }

        if (rating == null) {
            _errorEvent.value = R.string.empty_map_point_rating_error_message
            return
        }

        val requestBody = AddMapPointRequestBody(
            name = name,
            description = description,
            rating = rating,
            latitude = latLng.latitude,
            longitude = latLng.longitude
        )

        performAction(R.string.adding_map_point_error_message) {
            addMapPoint(requestBody)
        }
    }
}