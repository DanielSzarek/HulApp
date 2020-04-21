package pl.kamilszustak.hulapp.ui.main.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.IAdapter
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.domain.item.CityItem
import pl.kamilszustak.hulapp.domain.item.CountryItem
import pl.kamilszustak.hulapp.databinding.FragmentEditProfileBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.ui.dialog.city.CityChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.dialog.country.CountryChoiceBottomSheet
import pl.kamilszustak.hulapp.util.navigateUp
import javax.inject.Inject

class EditProfileFragment : BaseFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: EditProfileViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentEditProfileBinding

    private lateinit var cityChoiceBottomSheet: CityChoiceBottomSheet
    private lateinit var countryChoiceBottomSheet: CountryChoiceBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentEditProfileBinding>(
            inflater,
            R.layout.fragment_edit_profile,
            container,
            false
        ).apply {
            this.viewModel = this@EditProfileFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
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
        binding.cityNameEditText.setOnClickListener {
            cityChoiceBottomSheet.show(
                childFragmentManager,
                CityChoiceBottomSheet.tag
            )
        }

        binding.clearCityButton.setOnClickListener {
            viewModel.onClearCityButtonClick()
        }

        binding.countryNameEditText.setOnClickListener {
            countryChoiceBottomSheet.show(
                childFragmentManager,
                CountryChoiceBottomSheet.tag
            )
        }

        binding.clearCountryButton.setOnClickListener {
            viewModel.onClearCountryButtonClick()
        }

        binding.saveButton.setOnClickListener {
            viewModel.onSaveButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.userResource.data.observe(viewLifecycleOwner) { user ->
            viewModel.onUserLoaded(user)
        }

        viewModel.cityResource.data.observe(viewLifecycleOwner) { city ->
            viewModel.onCityLoaded(city)
        }

        viewModel.countryResource.data.observe(viewLifecycleOwner) { country ->
            viewModel.onCountryLoaded(country)
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }

        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            navigateUp()
        }
    }
}