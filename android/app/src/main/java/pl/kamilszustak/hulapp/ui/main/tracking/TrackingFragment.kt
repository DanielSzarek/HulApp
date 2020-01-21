package pl.kamilszustak.hulapp.ui.main.tracking

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
import kotlinx.android.synthetic.main.fragment_tracking.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentTrackingBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class TrackingFragment : BaseFragment(R.layout.fragment_tracking) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: TrackingViewModel by viewModels {
        viewModelFactory
    }

    private var start: Boolean = true

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

        getPermission()
        setListeners()
        observeViewModel()
    }

    private fun setListeners() {
        startTrackingButton.setOnClickListener {
            if (start) {
                motionLayout.transitionToEnd()
            } else {
                motionLayout.transitionToStart()
            }

            start = !start
        }
    }

    private fun observeViewModel() {
        viewModel.trackingState.observe(this) {
            when (it) {
                is TrackingState.Started -> {

                }

                is TrackingState.Paused -> {

                }

                is TrackingState.Stopped -> {
                }
            }
        }
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
            }
        }
    }

    private fun observeLocation() {
        viewModel.location.observe(this) {
            Timber.i(it.toString())
        }
    }
}