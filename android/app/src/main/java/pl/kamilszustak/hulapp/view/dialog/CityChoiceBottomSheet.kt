package pl.kamilszustak.hulapp.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.bottom_sheet_city_choice.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.BottomSheetCityChoiceBinding
import pl.kamilszustak.hulapp.util.getAndroidViewModelFactory
import pl.kamilszustak.hulapp.view.authorization.adapter.CityItem
import pl.kamilszustak.hulapp.viewmodel.dialog.CityChoiceViewModel

class CityChoiceBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val tag: String = "CITY_CHOICE_BOTTOM_SHEET"

        fun getInstance(): CityChoiceBottomSheet =
            CityChoiceBottomSheet()
    }

    private val viewModel: CityChoiceViewModel by viewModels {
        getAndroidViewModelFactory()
    }

    var listener: ClickListener<CityItem>? = null

    private lateinit var itemAdapter: ItemAdapter<CityItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<BottomSheetCityChoiceBinding>(
            inflater,
            R.layout.bottom_sheet_city_choice,
            container,
            false
        ).apply {
            this.viewModel = this@CityChoiceBottomSheet.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        itemAdapter = ItemAdapter()
        val fastAdapter = FastAdapter.with(itemAdapter)
        fastAdapter.onClickListener = listener
        citiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fastAdapter
        }
    }

    private fun setListeners() {
        closeButton.setOnClickListener {
            this.dismiss()
        }
    }

    private fun observeViewModel() {
        viewModel.cityName.observe(this) {
            viewModel.loadCitiesByName(it)
        }

        viewModel.cities.observe(this) {
            val items = arrayListOf<CityItem>()
            for (city in it) {
                items.add(CityItem(city))
            }

            itemAdapter.set(items)
        }
    }
}