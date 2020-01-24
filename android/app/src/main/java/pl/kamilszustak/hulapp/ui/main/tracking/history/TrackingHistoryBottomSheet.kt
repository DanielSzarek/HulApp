package pl.kamilszustak.hulapp.ui.main.tracking.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import kotlinx.android.synthetic.main.bottom_sheet_tracking_history.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.item.TrackItem
import pl.kamilszustak.hulapp.data.model.Track
import pl.kamilszustak.hulapp.ui.base.BaseBottomSheetDialogFragment
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.util.navigateUp
import pl.kamilszustak.hulapp.util.updateModels
import javax.inject.Inject

class TrackingHistoryBottomSheet : BaseBottomSheetDialogFragment(R.layout.bottom_sheet_tracking_history) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: TrackingHistoryViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var modelAdapter: ModelAdapter<Track, TrackItem>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        modelAdapter = ModelAdapter {
            TrackItem(it)
        }

        val listener = object : ClickListener<TrackItem> {
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

        val fastAdapter = FastAdapter.with(modelAdapter)
        fastAdapter.onClickListener = listener

        tracksRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun setListeners() {
        closeButton.setOnClickListener {
            navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.tracksResource.data.observe(this) {
            modelAdapter.updateModels(it.reversed())
        }

        viewModel.tracksResource.isLoading.observe(this) {
            if (it) {
                progressBar.show()
            } else {
                progressBar.hide()
            }
        }

        viewModel.tracksResource.error.observe(this) {
            view?.snackbar(it)
        }
    }

    private fun navigateToTrackDetailsFragment(trackId: Long) {
        val direction = TrackingHistoryBottomSheetDirections.actionTrackingHistoryBottomSheetToTrackDetailsFragment(trackId)
        navigateTo(direction)
    }
}