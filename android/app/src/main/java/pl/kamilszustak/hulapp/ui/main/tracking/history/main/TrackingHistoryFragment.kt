package pl.kamilszustak.hulapp.ui.main.tracking.history.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tracking_history.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentTrackingHistoryBinding
import pl.kamilszustak.hulapp.ui.main.tracking.history.BaseTrackingHistoryFragment
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.util.updateModels
import javax.inject.Inject

class TrackingHistoryFragment : BaseTrackingHistoryFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: TrackingHistoryViewModel by viewModels {
        viewModelFactory
    }

    override val recyclerView: RecyclerView
        get() = tracksRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentTrackingHistoryBinding>(
            inflater,
            R.layout.fragment_tracking_history,
            container,
            false
        ).apply {
            this.viewModel = this@TrackingHistoryFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModel()
    }

    private fun setListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun observeViewModel() {
        viewModel.tracksResource.data.observe(viewLifecycleOwner) { tracks ->
            modelAdapter.updateModels(tracks.reversed())
        }

        viewModel.tracksResource.error.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }
    }

    override fun navigateToTrackDetailsFragment(trackId: Long) {
        val direction = TrackingHistoryFragmentDirections.actionTrackingHistoryBottomSheetToTrackDetailsFragment(trackId)
        navigateTo(direction)
    }
}