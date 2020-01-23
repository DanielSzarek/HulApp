package pl.kamilszustak.hulapp.ui.main.tracking

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_tracking.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.model.LocationPoint
import pl.kamilszustak.hulapp.databinding.FragmentTrackingBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.dialog
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.util.polylineOptions
import pl.kamilszustak.hulapp.util.toLocationPoint
import javax.inject.Inject

class TrackingFragment : BaseFragment(R.layout.fragment_tracking) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: TrackingViewModel by viewModels {
        viewModelFactory
    }

    private var googleMap: GoogleMap? = null
    private val mapZoomLevel: Float = 15F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentTrackingBinding>(
            inflater,
            R.layout.fragment_tracking,
            container,
            false
        ).apply {
            this.viewModel = this@TrackingFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            getPermission()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        setListeners()
        observeViewModel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initializeMap() {
        val fragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        fragment?.getMapAsync(getOnMapReadyCallback())
    }

    private fun getPermission() {
        val permissions = arrayOf(
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_COARSE_LOCATION
        )

        askForPermissions(*permissions) {
            val allGranted = it.isAllGranted(*permissions)
            if (allGranted) {
                observeLocation()
                initializeMap()
            } else {
                getPermission()
            }
        }
    }

    private fun setListeners() {
        startTrackingButton.setOnClickListener {
            viewModel.onStartTrackingButtonClick()
        }

        endTrackingButton.setOnClickListener {
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

        mapTypeButton.setOnClickListener {
            viewModel.onMapTypeButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.trackingState.observe(this) {
            when (it) {
                is TrackingState.Started -> {
                    motionLayout.transitionToEnd()
                }

                is TrackingState.Paused -> {
                }

                is TrackingState.Ended -> {
                    motionLayout.transitionToStart()
                    navigateToTrackDetailsFragment(it.track.id)
                }
            }
        }

        viewModel.mapType.observe(this) {
            googleMap?.mapType = it
        }

        viewModel.locationPoints.observe(this) {
            val points = it.map { point ->
                point.toLatLng()
            }

            val polyline = polylineOptions {
                color(Color.BLUE)
                addAll(points)
            }

            googleMap?.addPolyline(polyline)
        }

        viewModel.error.observe(this) {
            view?.snackbar(it)
        }
    }

    private fun observeLocation() {
        viewModel.location.observe(this) {
            moveTo(it)
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

    private fun getOnMapReadyCallback(): OnMapReadyCallback {
        return OnMapReadyCallback {
            this.googleMap = it.apply {
                this.mapType = viewModel.getCurrentMapType()
                this.isMyLocationEnabled = true
            }
        }
    }

    private fun navigateToTrackDetailsFragment(trackId: Long) {
        val direction = TrackingFragmentDirections.actionTrackingFragmentToTrackDetailsFragment(trackId)
        navigateTo(direction)
    }
}