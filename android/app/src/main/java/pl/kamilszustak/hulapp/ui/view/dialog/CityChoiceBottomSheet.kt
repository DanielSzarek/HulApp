package pl.kamilszustak.hulapp.ui.view.dialog

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
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import kotlinx.android.synthetic.main.bottom_sheet_city_choice.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.model.City
import pl.kamilszustak.hulapp.databinding.BottomSheetCityChoiceBinding
import pl.kamilszustak.hulapp.util.getAndroidViewModelFactory
import pl.kamilszustak.hulapp.ui.view.authorization.item.CityItem
import pl.kamilszustak.hulapp.ui.viewmodel.dialog.CityChoiceViewModel
import pl.kamilszustak.hulapp.util.set

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
    }

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

        viewModel.cities.observe(this) {
            FastAdapterDiffUtil.set(modelAdapter, it)
        }

        viewModel.areCitiesLoading.observe(this) {
            if (it)
                progressBar.show()
            else
                progressBar.hide()
        }
    }
}