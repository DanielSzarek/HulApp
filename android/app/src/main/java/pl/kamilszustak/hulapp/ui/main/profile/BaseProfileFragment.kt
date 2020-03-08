package pl.kamilszustak.hulapp.ui.main.profile

import androidx.annotation.LayoutRes
import com.mikepenz.fastadapter.adapters.ModelAdapter
import pl.kamilszustak.hulapp.data.item.TrackItem
import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.ui.base.BaseFragment

abstract class BaseProfileFragment(@LayoutRes layoutResource: Int) : BaseFragment(layoutResource) {

    protected val trackModelAdapter: ModelAdapter<TrackEntity, TrackItem>

    init {
        trackModelAdapter = ModelAdapter { track ->
            TrackItem(track)
        }
    }
}