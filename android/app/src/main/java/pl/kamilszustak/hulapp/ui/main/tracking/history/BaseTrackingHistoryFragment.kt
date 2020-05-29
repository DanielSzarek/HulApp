package pl.kamilszustak.hulapp.ui.main.tracking.history

import androidx.annotation.LayoutRes
import com.mikepenz.fastadapter.adapters.ModelAdapter
import pl.kamilszustak.hulapp.domain.item.TrackItem
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity
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
}