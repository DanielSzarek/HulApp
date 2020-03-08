package pl.kamilszustak.hulapp.ui.main.tracking.history

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import pl.kamilszustak.hulapp.data.item.TrackItem
import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.ui.base.BaseFragment

abstract class BaseTrackingHistoryFragment : BaseFragment {

    constructor() : super()
    constructor(@LayoutRes layoutResource: Int) : super(layoutResource)

    protected val modelAdapter: ModelAdapter<TrackEntity, TrackItem>

    init {
        modelAdapter = ModelAdapter { track ->
            TrackItem(track)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
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

        recyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    protected abstract val recyclerView: RecyclerView

    protected abstract fun navigateToTrackDetailsFragment(trackId: Long)
}