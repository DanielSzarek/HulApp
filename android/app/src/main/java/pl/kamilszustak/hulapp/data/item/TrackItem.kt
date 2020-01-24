package pl.kamilszustak.hulapp.data.item

import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.layout_track_history_item.view.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.common.date.DateFormats
import pl.kamilszustak.hulapp.data.model.Track
import pl.kamilszustak.hulapp.util.asTimeString

class TrackItem(track: Track) : ModelAbstractItem<Track, TrackItem.ViewHolder>(track) {

    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int
        get() = R.id.fastadapter_track_item_id

    override val layoutRes: Int
        get() = R.layout.layout_track_history_item

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(
        private val view: View
    ) : FastAdapter.ViewHolder<TrackItem>(view) {

        private val startDateTextView: TextView = view.startDateTextView
        private val distanceTextView: TextView = view.distanceTextView
        private val durationTextView: TextView = view.durationTextView

        override fun bindView(item: TrackItem, payloads: MutableList<Any>) {
            startDateTextView.text = DateFormats.dateFormat.format(item.model.startDate)
            distanceTextView.text = view.context.getString(
                R.string.track_distance_value,
                item.model.distance.toString()
            )
            durationTextView.text = item.model.duration.asTimeString()
        }

        override fun unbindView(item: TrackItem) {
            startDateTextView.text = null
            distanceTextView.text = null
            durationTextView.text = null
        }
    }
}