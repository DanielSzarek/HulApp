package pl.kamilszustak.hulapp.ui.main.profile

import androidx.annotation.LayoutRes
import com.mikepenz.fastadapter.adapters.ModelAdapter
import pl.kamilszustak.hulapp.domain.item.TrackItem
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity
import pl.kamilszustak.hulapp.ui.base.BaseFragment

abstract class BaseProfileFragment : BaseFragment {

    constructor() : super()
    constructor(@LayoutRes layoutResource: Int) : super(layoutResource)

    protected val trackModelAdapter: ModelAdapter<TrackEntity, TrackItem> by lazy {
        ModelAdapter<TrackEntity, TrackItem> { track ->
            TrackItem(track)
        }
    }
}