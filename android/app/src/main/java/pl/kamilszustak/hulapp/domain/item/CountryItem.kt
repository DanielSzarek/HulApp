package pl.kamilszustak.hulapp.domain.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.ItemCountriesListBinding
import pl.kamilszustak.hulapp.domain.model.Country

class CountryItem(country: Country) : ModelAbstractBindingItem<Country, ItemCountriesListBinding>(country) {

    override var identifier: Long
        get() = this.model.id
        set(value) {}

    override val type: Int
        get() = R.id.fastadapter_country_item

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemCountriesListBinding =
        ItemCountriesListBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemCountriesListBinding, payloads: List<Any>) {
        binding.countryNameTextView.text = this.model.name
    }

    override fun unbindView(binding: ItemCountriesListBinding) {
        binding.countryNameTextView.text = null
    }
}