package pl.kamilszustak.hulapp.ui.main.tracking

import android.app.Application
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emreeran.locationlivedata.LocationLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.yashovardhan99.timeit.Stopwatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.model.LocationPoint
import pl.kamilszustak.hulapp.data.model.Track
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.base.BaseViewModel
import pl.kamilszustak.hulapp.util.round
import pl.kamilszustak.hulapp.util.toLocationPoint
import pl.kamilszustak.hulapp.util.withIOContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TrackingViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository
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
    private var currentDuration: Long = 0
    private var lastLocation: Location? = null
    private var isFirstLocationChange: Boolean = true
    private val points: MutableList<LocationPoint> = arrayListOf()
    private var startDate: Date = Date()
    private val stopwatch: Stopwatch = Stopwatch()

    private val mapTypes: List<Int> = listOf(
        GoogleMap.MAP_TYPE_NORMAL,
        GoogleMap.MAP_TYPE_SATELLITE,
        GoogleMap.MAP_TYPE_TERRAIN
    )
    private var currentMapTypeIndex: Int = 0

    private val _trackingState: UniqueLiveData<TrackingState> = UniqueLiveData()
    val trackingState: LiveData<TrackingState> = _trackingState

    private val _distance: MediatorLiveData<Double> = MediatorLiveData()
    val distance: LiveData<Double> = _distance

    private val _mapType: UniqueLiveData<Int> = UniqueLiveData()
    val mapType: LiveData<Int> = _mapType

    private val _locationPoints: MutableLiveData<List<LocationPoint>> = MutableLiveData()
    val locationPoints: LiveData<List<LocationPoint>> = _locationPoints

    private val _duration: UniqueLiveData<Long> = UniqueLiveData(currentDuration)
    val duration: LiveData<Long> = _duration

    private val _isLoading: UniqueLiveData<Boolean> = UniqueLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: SingleLiveData<String> = SingleLiveData()
    val error: LiveData<String> = _error

    private val _trackSaved: SingleLiveData<Track> = SingleLiveData()
    val trackSaved: LiveData<Track> = _trackSaved

    init {
        initializeTracking()
        initializeDistance()
        initializeStopwatch()
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

    private fun initializeTracking() {
        currentTrackingState = TrackingState.Idle
        currentDistance = 0.0
        currentDuration = 0
        lastLocation = null
        isFirstLocationChange = true
        points.clear()
        startDate = Date()

        changeDistance(currentDistance)
        changeTrackingState(TrackingState.Idle)
    }

    private fun initializeDistance() {
        changeDistance(currentDistance)
        _distance.addSource(location) {
            if (currentTrackingState.isStarted) {
                if (!isFirstLocationChange) {
                    val newDistanceInMeters = lastLocation?.distanceTo(it) ?: 0F
                    currentDistance += newDistanceInMeters / 1000
                    _distance.value = currentDistance.round()
                } else {
                    lastLocation = it
                    isFirstLocationChange = false
                }

                addLocationPoint(it.toLocationPoint())
            }
        }
    }

    private fun initializeStopwatch() {
        stopwatch.apply {
            this.clockDelay = 1000
            this.setOnTickListener {
                incrementDuration()
            }
        }
    }

    private fun addLocationPoint(locationPoint: LocationPoint) {
        points.add(locationPoint)
        _locationPoints.value = points
    }

    private fun incrementDuration() {
        currentDuration++
        _duration.setValue(currentDuration)
    }

    private fun changeTrackingState(trackingState: TrackingState) {
        currentTrackingState = trackingState
        _trackingState.setValue(currentTrackingState)
    }

    private fun changeDistance(distance: Double) {
        currentDistance = distance
        _distance.value = currentDistance
    }

    fun onStartTrackingButtonClick() {
        val state = when (currentTrackingState) {
            is TrackingState.Idle -> {
                if (!stopwatch.isStarted) {
                    stopwatch.start()
                }
                startDate = Date()
                TrackingState.Started
            }

            is TrackingState.Started -> {
                if (stopwatch.isStarted) {
                    stopwatch.pause()
                }
                TrackingState.Paused
            }

            is TrackingState.Paused -> {
                if (stopwatch.isPaused) {
                    stopwatch.resume()
                }
                TrackingState.Started
            }

            is TrackingState.Ended -> {
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                TrackingState.Started
            }
        }

        changeTrackingState(state)
    }

    fun onEndTrackingButtonClick() {
        if (stopwatch.isStarted) {
            stopwatch.stop()
        }

        val track = Track(
            startDate,
            Date(),
            currentDuration,
            currentDistance.round()
        )

        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true

            val result = withIOContext {
                trackRepository.save(track)
            }

            if (result.isSuccess) {
                val resultTrack = result.getOrNull()
                if (resultTrack != null) {
                    changeTrackingState(TrackingState.Ended)
                    initializeTracking()
                    _trackSaved.value = resultTrack
                }
            } else {
                _error.value = "Wystąpił błąd podczas zapisywania trasy"
            }

            _isLoading.value = false
        }
    }

    fun onMapTypeButtonClick() {
        if (currentMapTypeIndex == mapTypes.size - 1) {
            currentMapTypeIndex = 0
        } else {
            currentMapTypeIndex++
        }

        _mapType.setValue(mapTypes[currentMapTypeIndex])
    }

    fun getCurrentMapType(): Int = mapTypes[currentMapTypeIndex]
}