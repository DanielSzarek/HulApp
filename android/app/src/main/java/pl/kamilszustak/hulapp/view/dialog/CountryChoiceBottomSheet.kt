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

class CountryChoiceBottomSheet(
    private val listener: ClickListener<CountryItem>
): BottomSheetDialogFragment() {

    private val viewModel: CountryChoiceViewModel by viewModels {
        getAndroidViewModelFactory()
    }

    private lateinit var itemAdapter: ItemAdapter<CountryItem>

    companion object {
        const val tag: String = "COUNTRY_CHOICE_BOTTOM_SHEET"
    }

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

//        val bottomSheetDialog = dialog as BottomSheetDialog?
//        val layout = bottomSheetDialog?.countryChoiceLinearLayout
//        if (layout != null)
//            BottomSheetBehavior.from(layout).state = BottomSheetBehavior.STATE_EXPANDED
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