package pl.kamilszustak.hulapp.ui.main.tracking

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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_tracking.*
import org.jetbrains.anko.support.v4.toast
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentTrackingBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.toLatLng
import pl.kamilszustak.hulapp.util.toLocationPoint
import timber.log.Timber
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

        initializeMap()
        getPermission()
        setListeners()
        observeViewModel()
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
            viewModel.onEndTrackingButtonClick()
        }

        userLocationButton.setOnClickListener {
            viewModel.onUserLocationButtonClick()
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
                }
            }
        }

        viewModel.moveToUserLocation.observe(this) {
            moveTo(it)
        }
    }

    private fun observeLocation() {
        viewModel.location.observe(this) {
            moveTo(it)
        }

        viewModel.distance.observe(this) {
            Timber.i(it.toString())
        }
    }

    private fun moveTo(location: Location) {
        val position = location.toLatLng()
        val marker = MarkerOptions().position(position)
        val cameraLocation = CameraUpdateFactory.newLatLngZoom(position, mapZoomLevel)

        googleMap?.apply {
            clear()
            addMarker(marker)
            animateCamera(cameraLocation)
        }
    }

    private fun getOnMapReadyCallback(): OnMapReadyCallback {
        return OnMapReadyCallback {
            this.googleMap = it
        }
    }
}