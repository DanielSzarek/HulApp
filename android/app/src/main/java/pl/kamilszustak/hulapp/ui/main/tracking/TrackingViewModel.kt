package pl.kamilszustak.hulapp.ui.main.tracking

import android.app.Application
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    private var currentTrackingState: TrackingState = TrackingState.Idle
    private var currentDistance: Double = 0.0
    private var lastLocation: Location? = null
    private var isFirstLocationChange: Boolean = true

    private val _trackingState: UniqueLiveData<TrackingState> = UniqueLiveData()
    val trackingState: LiveData<TrackingState> = _trackingState

    private val _distance: MediatorLiveData<Double> = MediatorLiveData()
    val distance: LiveData<Double> = _distance

    init {
        initializeDistance()
    }

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

    private fun initializeDistance() {
        _distance.value = currentDistance
        _distance.addSource(location) {
            if (!isFirstLocationChange) {
                val newDistance = lastLocation?.distanceTo(it) ?: 0F
                currentDistance += newDistance
                _distance.value = currentDistance
            } else {
                lastLocation = it
                isFirstLocationChange = false
            }
        }
    }

    private fun changeTrackingState(trackingState: TrackingState) {
        currentTrackingState = trackingState
        _trackingState.setValue(currentTrackingState)
    }

    fun onStartTrackingButtonClick() {
        val state = when (currentTrackingState) {
            is TrackingState.Idle -> {
                TrackingState.Started
            }

            is TrackingState.Started -> {
                TrackingState.Paused
            }

            is TrackingState.Paused -> {
                TrackingState.Started
            }

            is TrackingState.Ended -> {
                TrackingState.Started
            }
        }

        changeTrackingState(state)
    }

    fun onEndTrackingButtonClick() {
        val state = TrackingState.Ended()
        changeTrackingState(state)
    }
}