package pl.kamilszustak.hulapp.ui.main.tracking

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.afollestad.assent.Permission
import com.afollestad.assent.askForPermissions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.toast
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentTrackingBinding
import pl.kamilszustak.hulapp.domain.model.LocationPoint
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.dialog
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.util.polylineOptions
import pl.kamilszustak.hulapp.util.toLocationPoint
import javax.inject.Inject

class TrackingFragment : BaseFragment(), OnMapReadyCallback {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: TrackingViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentTrackingBinding

    private var googleMap: GoogleMap? = null
    private val mapZoomLevel: Float = 15F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTrackingBinding>(
            inflater,
            R.layout.fragment_tracking,
            container,
            false
        ).apply {
            this.viewModel = this@TrackingFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_tracking_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refreshItem -> {
                viewModel.onRefresh()
                true
            }

            R.id.trackingHistoryItem -> {
                navigateToTrackingHistoryBottomSheet()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        getPermission()
        setListeners()
        observeViewModel()
    }

    private fun initializeMap() {
        val fragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        fragment?.getMapAsync(this)
    }

    private fun getPermission() {
        val permissions = arrayOf(
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_COARSE_LOCATION
        )

        askForPermissions(*permissions) { result ->
            val allGranted = result.isAllGranted(*permissions)
            if (allGranted) {
                observeLocation()
                initializeMap()
            } else {
                getPermission()
            }
        }
    }

    private fun setListeners() {
        binding.startTrackingButton.setOnClickListener {
            viewModel.onStartTrackingButtonClick()
        }

        binding.endTrackingButton.setOnClickListener {
            dialog {
                title(R.string.tracking_end_title)
                message(R.string.tracking_end_message)
                positiveButton(R.string.yes) {
                    viewModel.onEndTrackingButtonClick()
                }
                negativeButton(R.string.no) {
                    it.dismiss()
                }
            }
        }

        binding.mapTypeButton.setOnClickListener {
            viewModel.onMapTypeButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.trackingState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TrackingState.Started -> {
                    binding.motionLayout.transitionToEnd()
                }

                is TrackingState.Paused -> {
                }

                is TrackingState.Ended -> {
                    binding.motionLayout.transitionToStart()
                }
            }
        }

        viewModel.mapType.observe(viewLifecycleOwner) { type ->
            googleMap?.mapType = type
        }

        viewModel.locationPoints.observe(viewLifecycleOwner) { locations ->
            val points = locations.map { point ->
                point.toLatLng()
            }

            val polyline = polylineOptions {
                color(Color.BLUE)
                addAll(points)
            }

            googleMap?.addPolyline(polyline)
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }

        viewModel.trackSaved.observe(viewLifecycleOwner) { track ->
            googleMap?.clear()
            navigateToTrackDetailsFragment(track.id)
        }
    }

    private fun observeLocation() {
        viewModel.location.observe(viewLifecycleOwner) { location ->
            moveTo(location)
        }
    }

    private fun observeMarkers() {
        viewModel.mapPointsMarkers.observe(viewLifecycleOwner) { markers ->
            markers.forEach { marker ->
                val addedMarker = googleMap?.addMarker(marker)
                if (addedMarker != null) {
                    viewModel.onMarkerAdded(addedMarker)
                }
            }
        }
    }

    private fun moveTo(locationPoint: LocationPoint) {
        val position = locationPoint.toLatLng()
        val cameraLocation = CameraUpdateFactory.newLatLngZoom(position, mapZoomLevel)

        googleMap?.apply {
            animateCamera(cameraLocation)
        }
    }

    private fun moveTo(location: Location) {
        moveTo(location.toLocationPoint())
    }

    override fun onMapReady(map: GoogleMap?) {
        this.googleMap = map?.apply {
            this.mapType = viewModel.getCurrentMapType()
            this.isMyLocationEnabled = true

            this.setOnMapLongClickListener { latLng ->
                if (latLng != null) {
                    navigateToAddMapPointFragment(latLng)
                } else {
                    toast("Wystąpił błąd z wybraną lokalizacją na mapie")
                }
            }

            this.setOnMarkerClickListener { marker ->
                val mapPointId = marker?.snippet?.toLongOrNull()
                if (mapPointId != null) {
                    navigateToMapPointDetailsFragment(marker.snippet.toLong())
                    true
                } else {
                    false
                }
            }
        }

        observeMarkers()
    }

    private fun navigateToTrackDetailsFragment(trackId: Long) {
        val direction = TrackingFragmentDirections.actionTrackingFragmentToTrackDetailsFragment(trackId)
        navigateTo(direction)
    }

    private fun navigateToTrackingHistoryBottomSheet() {
        val direction = TrackingFragmentDirections.actionTrackingFragmentToTrackingHistoryFragment()
        navigateTo(direction)
    }

    private fun navigateToAddMapPointFragment(latLng: LatLng) {
        val direction = TrackingFragmentDirections.actionTrackingFragmentToAddMapPointFragment(latLng)
        navigateTo(direction)
    }

    private fun navigateToMapPointDetailsFragment(mapPointId: Long) {
        val isMine = viewModel.isMapPointMine(mapPointId)
        val direction = TrackingFragmentDirections.actionTrackingFragmentToMapPointDetailsFragment(mapPointId, isMine)
        navigateTo(direction)
    }
}