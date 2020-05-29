package pl.kamilszustak.hulapp.ui.main.tracking.history.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.domain.item.TrackItem
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

    private lateinit var binding: FragmentTrackingHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTrackingHistoryBinding>(
            inflater,
            R.layout.fragment_tracking_history,
            container,
            false
        ).apply {
            this.viewModel = this@TrackingHistoryFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        val fastAdapter = FastAdapter.with(modelAdapter).apply {
            this.onClickListener = object : ClickListener<TrackItem> {
                override fun invoke(
                    v: View?,
                    adapter: IAdapter<TrackItem>,
                    item: TrackItem,
                    position: Int
                ): Boolean {
                    navigateToTrackDetailsFragment(item.model.id)
                    return true
                }
            }
        }

        binding.tracksRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
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

    fun navigateToTrackDetailsFragment(trackId: Long) {
        val direction = TrackingHistoryFragmentDirections.actionTrackingHistoryFragmentToTrackDetailsFragment(trackId)
        navigateTo(direction)
    }
}