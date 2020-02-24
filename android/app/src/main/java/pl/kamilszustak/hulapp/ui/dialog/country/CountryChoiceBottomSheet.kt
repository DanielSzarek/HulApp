package pl.kamilszustak.hulapp.ui.dialog.country

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
import kotlinx.android.synthetic.main.bottom_sheet_country_choice.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.model.Country
import pl.kamilszustak.hulapp.databinding.BottomSheetCountryChoiceBinding
import pl.kamilszustak.hulapp.data.item.CountryItem
import pl.kamilszustak.hulapp.ui.base.BaseBottomSheetDialogFragment
import pl.kamilszustak.hulapp.util.navigateUp
import pl.kamilszustak.hulapp.util.set
import pl.kamilszustak.hulapp.util.updateModels
import javax.inject.Inject

class CountryChoiceBottomSheet : BaseBottomSheetDialogFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: CountryChoiceViewModel by viewModels {
        viewModelFactory
    }

    var listener: ClickListener<CountryItem>? = null

    private lateinit var modelAdapter: ModelAdapter<Country, CountryItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<BottomSheetCountryChoiceBinding>(
            inflater,
            R.layout.bottom_sheet_country_choice,
            container,
            false
        ).apply {
            this.viewModel = this@CountryChoiceBottomSheet.viewModel
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
            CountryItem(it)
        }
        val fastAdapter = FastAdapter.with(modelAdapter)
        fastAdapter.onClickListener = listener
        countriesRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun setListeners() {
        closeButton.setOnClickListener {
            navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.countryName.observe(viewLifecycleOwner) { name ->
            viewModel.loadCountriesByName(name)
        }

        viewModel.countriesResource.data.observe(viewLifecycleOwner) { countries ->
            modelAdapter.updateModels(countries)
        }

        viewModel.countriesResource.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressBar.show()
            } else {
                progressBar.hide()
            }
        }
    }

    companion object {
        const val tag: String = "COUNTRY_CHOICE_BOTTOM_SHEET"

        fun getInstance(): CountryChoiceBottomSheet =
            CountryChoiceBottomSheet()
    }
}