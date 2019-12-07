package pl.kamilszustak.hulapp.view.authorization.adapter

import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.layout_cities_list_item.view.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.model.City

class CityItem(
    val city: City
) : AbstractItem<CityItem.ViewHolder>() {

    override val type: Int
        get() = R.id.fastadapter_city_item_id

    override val layoutRes: Int
        get() = R.layout.layout_cities_list_item

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<CityItem>(view) {

        private val cityNameTextView: TextView = view.cityNameTextView

        override fun bindView(item: CityItem, payloads: MutableList<Any>) {
            cityNameTextView.text = item.city.name
        }

        override fun unbindView(item: CityItem) {
            cityNameTextView.text = null
        }
    }
}