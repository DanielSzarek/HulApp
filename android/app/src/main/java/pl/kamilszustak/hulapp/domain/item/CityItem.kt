package pl.kamilszustak.hulapp.domain.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.ItemCitiesListBinding
import pl.kamilszustak.hulapp.domain.model.City

class CityItem(city: City) : ModelAbstractBindingItem<City, ItemCitiesListBinding>(city) {

    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int
        get() = R.id.fastadapter_city_item

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemCitiesListBinding =
        ItemCitiesListBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemCitiesListBinding, payloads: List<Any>) {
        binding.cityNameTextView.text = this.model.name
    }

    override fun unbindView(binding: ItemCitiesListBinding) {
        binding.cityNameTextView.text = null
    }
}