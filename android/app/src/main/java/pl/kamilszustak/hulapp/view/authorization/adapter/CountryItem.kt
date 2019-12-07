package pl.kamilszustak.hulapp.view.authorization.adapter

import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.layout_countries_list_item.view.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.model.Country

class CountryItem(
    val country: Country
) : AbstractItem<CountryItem.ViewHolder>() {

    override val type: Int
        get() = R.id.fastadapter_country_item_id

    override val layoutRes: Int
        get() =  R.layout.layout_countries_list_item

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)


    class ViewHolder(view: View): FastAdapter.ViewHolder<CountryItem>(view) {

        val countryNameTextView: TextView = view.countryNameTextView

        override fun bindView(item: CountryItem, payloads: MutableList<Any>) {
            countryNameTextView.text = item.country.name
        }

        override fun unbindView(item: CountryItem) {
            countryNameTextView.text = null
        }
    }
}