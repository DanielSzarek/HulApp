package pl.kamilszustak.hulapp.ui.main.tracking

import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import com.emreeran.locationlivedata.LocationLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.yashovardhan99.timeit.Stopwatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.livedata.ResourceDataSource
import pl.kamilszustak.hulapp.common.livedata.SingleLiveData
import pl.kamilszustak.hulapp.common.livedata.UniqueLiveData
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.domain.model.LocationPoint
import pl.kamilszustak.hulapp.domain.model.Track
import pl.kamilszustak.hulapp.domain.model.point.MapPoint
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity
import pl.kamilszustak.hulapp.domain.usecase.point.GetAllMapPointsUseCase
import pl.kamilszustak.hulapp.ui.base.viewmodel.StateViewModel
import pl.kamilszustak.hulapp.util.round
import pl.kamilszustak.hulapp.util.toLocationPoint
import pl.kamilszustak.hulapp.util.withIOContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TrackingViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository,
    private val getAllMapPoints: GetAllMapPointsUseCase
) : StateViewModel(application) {

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

    private val _trackSaved: SingleLiveData<TrackEntity> = SingleLiveData()
    val trackSaved: LiveData<TrackEntity> = _trackSaved

    private val markers: MutableList<Marker> = mutableListOf()
    private val mapPointsResource: ResourceDataSource<List<MapPoint>> = ResourceDataSource()
    val mapPointsMarkers: LiveData<List<MarkerOptions>> = mapPointsResource.data.map { points ->
        createMarkers(points)
    }

    init {
        initializeTracking()
        initializeDistance()
        initializeStopwatch()

        mapPointsResource.setFlowSource {
            getAllMapPoints()
        }
    }

    fun onRefresh() {
        mapPointsResource.refresh()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun createMarkers(points: List<MapPoint>): List<MarkerOptions> {
        markers.run {
            forEach { it.remove() }
            clear()
        }

        return buildList {
            points.forEach { point ->
                val icon = BitmapDescriptorFactory.fromResource(point.rating.markerIconResource)
                val marker = MarkerOptions()
                    .icon(icon)
                    .snippet(point.id.toString())
                    .position(point.location)

                add(marker)
            }
        }
    }

    fun onMarkerAdded(marker: Marker) {
        markers.run {
            removeAll { it.snippet == marker.snippet }
            add(marker)
        }
    }

    fun isMapPointMine(pointId: Long): Boolean {
        val mapPoint = mapPointsResource.data.value?.firstOrNull { it.id == pointId}
        return mapPoint?.isMine ?: false
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
        _distance.addSource(location) { currentLocation ->
            if (currentTrackingState.isStarted) {
                if (!isFirstLocationChange) {
                    val newDistanceInMeters = lastLocation?.distanceTo(currentLocation) ?: 0F
                    currentDistance += newDistanceInMeters / 1000
                    _distance.value = currentDistance.round()
                } else {
                    isFirstLocationChange = false
                }

                lastLocation = currentLocation
                addLocationPoint(currentLocation.toLocationPoint())
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
        _duration.value = currentDuration
    }

    private fun changeTrackingState(trackingState: TrackingState) {
        currentTrackingState = trackingState
        _trackingState.value = currentTrackingState
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
                _errorEvent.value = R.string.track_saving_error_message
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

        _mapType.value = mapTypes[currentMapTypeIndex]
    }

    fun getCurrentMapType(): Int = mapTypes[currentMapTypeIndex]
}