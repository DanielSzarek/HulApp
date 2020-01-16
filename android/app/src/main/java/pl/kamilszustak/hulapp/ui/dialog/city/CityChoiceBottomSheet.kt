package pl.kamilszustak.hulapp.ui.dialog.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import kotlinx.android.synthetic.main.bottom_sheet_city_choice.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.databinding.BottomSheetCityChoiceBinding
import pl.kamilszustak.hulapp.data.item.CityItem
import pl.kamilszustak.hulapp.ui.base.BaseBottomSheetDialogFragment
import pl.kamilszustak.hulapp.util.set
import pl.kamilszustak.hulapp.util.updateModels
import javax.inject.Inject

class CityChoiceBottomSheet : BaseBottomSheetDialogFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: CityChoiceViewModel by viewModels {
        viewModelFactory
    }


    private lateinit var modelAdapter: ModelAdapter<City, CityItem>

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
    }var listener: ClickListener<CityItem>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = true
        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        modelAdapter = ModelAdapter {
            CityItem(it)
        }
        val fastAdapter = FastAdapter.with(modelAdapter)
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

        viewModel.citiesResource.data.observe(this) {
            modelAdapter.updateModels(it)
        }

        viewModel.citiesResource.isLoading.observe(this) {
            if (it)
                progressBar.show()
            else
                progressBar.hide()
        }
    }

    companion object {
        const val tag: String = "CITY_CHOICE_BOTTOM_SHEET"

        fun getInstance(): CityChoiceBottomSheet =
            CityChoiceBottomSheet()
    }
}