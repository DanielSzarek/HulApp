package pl.kamilszustak.hulapp.ui.main.profile

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.IAdapter
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.item.CityItem
import pl.kamilszustak.hulapp.data.item.CountryItem
import pl.kamilszustak.hulapp.databinding.FragmentEditProfileBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.ui.dialog.city.CityChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.dialog.country.CountryChoiceBottomSheet
import javax.inject.Inject

class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: EditProfileViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var cityChoiceBottomSheet: CityChoiceBottomSheet
    private lateinit var countryChoiceBottomSheet: CountryChoiceBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentEditProfileBinding>(
            inflater,
            R.layout.fragment_edit_profile,
            container,
            false
        ).apply {
            this.viewModel = this@EditProfileFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBottomSheet()
        setListeners()
        observeViewModel()
    }

    private fun initializeBottomSheet() {
        val onCityClickListener = object : ClickListener<CityItem> {
            override fun invoke(
                v: View?,
                adapter: IAdapter<CityItem>,
                item: CityItem,
                position: Int
            ): Boolean {
                viewModel.onCityChoosen(item.model)
                cityChoiceBottomSheet.dismiss()

                return true
            }
        }

        cityChoiceBottomSheet = CityChoiceBottomSheet.getInstance().apply {
            this.listener = onCityClickListener
        }

        val onCountryClickListener = object : ClickListener<CountryItem> {
            override fun invoke(
                v: View?,
                adapter: IAdapter<CountryItem>,
                item: CountryItem,
                position: Int
            ): Boolean {
                viewModel.onCountryChoosen(item.model)
                countryChoiceBottomSheet.dismiss()

                return true
            }
        }

        countryChoiceBottomSheet = CountryChoiceBottomSheet.getInstance().apply {
            this.listener = onCountryClickListener
        }
    }

    private fun setListeners() {
        cityNameEditText.setOnClickListener {
            cityChoiceBottomSheet.show(
                childFragmentManager,
                CityChoiceBottomSheet.tag
            )
        }

        clearCityButton.setOnClickListener {
            viewModel.onClearCityButtonClick()
        }

        countryNameEditText.setOnClickListener {
            countryChoiceBottomSheet.show(
                childFragmentManager,
                CountryChoiceBottomSheet.tag
            )
        }

        clearCountryButton.setOnClickListener {
            viewModel.onClearCountryButtonClick()
        }

        saveButton.setOnClickListener {
            viewModel.onSaveButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.userResource.data.observe(this) {
            viewModel.onUserLoaded(it)
        }
    }
}