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
import kotlinx.android.synthetic.main.bottom_sheet_country_choice.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.BottomSheetCountryChoiceBinding
import pl.kamilszustak.hulapp.util.getAndroidViewModelFactory
import pl.kamilszustak.hulapp.view.authorization.adapter.CountryItem
import pl.kamilszustak.hulapp.viewmodel.dialog.CountryChoiceViewModel
import timber.log.Timber

class CountryChoiceBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val tag: String = "COUNTRY_CHOICE_BOTTOM_SHEET"

        fun getInstance(): CountryChoiceBottomSheet =
            CountryChoiceBottomSheet()
    }

    private val viewModel: CountryChoiceViewModel by viewModels {
        getAndroidViewModelFactory()
    }

    var listener: ClickListener<CountryItem>? = null

    private lateinit var itemAdapter: ItemAdapter<CountryItem>

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

        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun initializeRecyclerView() {
        itemAdapter = ItemAdapter()
        val fastAdapter = FastAdapter.with(itemAdapter)
        fastAdapter.onClickListener = listener
        countriesRecyclerView.apply {
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
        viewModel.countryName.observe(this) {
            viewModel.loadCountriesByName(it)
        }

        viewModel.countries.observe(this) {
            Timber.i(it.toString())
            val items = arrayListOf<CountryItem>()
            for (country in it) {
                items.add(CountryItem(country))
            }

            itemAdapter.set(items)
        }
    }
}