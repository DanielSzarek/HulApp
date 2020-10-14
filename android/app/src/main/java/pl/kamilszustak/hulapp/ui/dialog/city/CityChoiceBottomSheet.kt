package pl.kamilszustak.hulapp.ui.dialog.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.BottomSheetCityChoiceBinding
import pl.kamilszustak.hulapp.domain.item.CityItem
import pl.kamilszustak.hulapp.domain.model.City
import pl.kamilszustak.hulapp.ui.base.BaseBottomSheetDialogFragment
import pl.kamilszustak.hulapp.util.updateModels
import javax.inject.Inject

class CityChoiceBottomSheet : BaseBottomSheetDialogFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: CityChoiceViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: BottomSheetCityChoiceBinding

    var listener: ClickListener<CityItem>? = null

    private lateinit var modelAdapter: ModelAdapter<City, CityItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<BottomSheetCityChoiceBinding>(
            inflater,
            R.layout.bottom_sheet_city_choice,
            container,
            false
        ).apply {
            this.viewModel = this@CityChoiceBottomSheet.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        modelAdapter = ModelAdapter {
            CityItem(it)
        }

        val fastAdapter = FastAdapter.with(modelAdapter).apply {
            this.onClickListener = listener
        }

        binding.citiesRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun setListeners() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun observeViewModel() {
        viewModel.cityName.observe(viewLifecycleOwner) { name ->
            viewModel.loadCitiesByName(name)
        }

        viewModel.citiesResource.data.observe(viewLifecycleOwner) { cities ->
            modelAdapter.updateModels(cities)
        }

        viewModel.citiesResource.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }
    }

    companion object {
        const val tag: String = "CITY_CHOICE_BOTTOM_SHEET"

        fun getInstance(): CityChoiceBottomSheet =
            CityChoiceBottomSheet()
    }
}