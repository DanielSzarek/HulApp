package pl.kamilszustak.hulapp.ui.authentication.signup

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
import pl.kamilszustak.hulapp.databinding.FragmentSignUpBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.ui.dialog.city.CityChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.dialog.country.CountryChoiceBottomSheet
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.util.navigateUp
import javax.inject.Inject

class SignUpFragment : BaseFragment() {

    private lateinit var countryChoiceBottomSheet: CountryChoiceBottomSheet
    private lateinit var cityChoiceBottomSheet: CityChoiceBottomSheet

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: SignUpViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        ).apply {
            this.viewModel = this@SignUpFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBottomSheets()
        setListeners()
        observeViewModel()
    }

    private fun initializeBottomSheets() {
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
        binding.signUpButton.setOnClickListener {
            viewModel.onSignUpButtonClick()
        }

        binding.backToLoginPageButton.setOnClickListener {
            navigateUp()
        }

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
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                with(binding) {
                    motionLayout.transitionToEnd()
                    signUpButton.isEnabled = false
                    progressBar.show()
                }
            } else {
                with(binding) {
                    motionLayout.transitionToStart()
                    signUpButton.isEnabled = true
                    progressBar.hide()
                }
            }
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }

        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            navigateToSignUpCompletedFragment()
        }
    }

    private fun navigateToSignUpCompletedFragment() {
        val direction = SignUpFragmentDirections.actionSignUpFragmentToSignUpCompleted()
        navigateTo(direction)
    }
}