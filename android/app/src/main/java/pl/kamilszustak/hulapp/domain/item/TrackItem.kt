package pl.kamilszustak.hulapp.domain.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.date.DateFormats
import pl.kamilszustak.hulapp.databinding.ItemTracksHistoryBinding
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity
import pl.kamilszustak.hulapp.util.asTimeString

class TrackItem(track: TrackEntity) : ModelAbstractBindingItem<TrackEntity, ItemTracksHistoryBinding>(track) {

    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int
        get() = R.id.fastadapter_track_item

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemTracksHistoryBinding =
        ItemTracksHistoryBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemTracksHistoryBinding, payloads: List<Any>) {
        with(binding) {
            startDateTextView.text = DateFormats.fullDateFormat.format(model.startDate)
            val distanceAndTime = distanceAndTimeTextView.context.getString(
                R.string.track_distance_and_time,
                model.distance.toString(),
                model.duration.asTimeString()
            )
            distanceAndTimeTextView.text = distanceAndTime
        }
    }

    override fun unbindView(binding: ItemTracksHistoryBinding) {
        with(binding) {
            startDateTextView.text = null
            distanceAndTimeTextView.text = null
        }
    }
}