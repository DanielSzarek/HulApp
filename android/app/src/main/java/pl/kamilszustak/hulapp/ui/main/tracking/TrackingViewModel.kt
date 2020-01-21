package pl.kamilszustak.hulapp.ui.main.tracking

import android.app.Application
import androidx.lifecycle.LiveData
import com.emreeran.locationlivedata.LocationLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationRequest
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class TrackingViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {

    val location: LocationLiveData = LocationLiveData.create(
        context = application,
        interval = 3000,
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY,
        expirationTime = 10000,
        fastestInterval = 1000,
        numUpdates = 10,
        smallestDisplacement = 10F,
        onErrorCallback = getOnErrorCallback()
    )

    private val _trackingState: UniqueLiveData<TrackingState> = UniqueLiveData()
    val trackingState: LiveData<TrackingState> = _trackingState


    private fun getOnErrorCallback(): LocationLiveData.OnErrorCallback {
        return object : LocationLiveData.OnErrorCallback {
            override fun onLocationSettingsException(e: ApiException) {
                Timber.e(e)
            }

            override fun onPermissionsMissing() {
                Timber.e("Permission missing")
            }
        }
    }
}