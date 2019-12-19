package pl.kamilszustak.hulapp.ui.view.authorization

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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentSignUpBinding
import pl.kamilszustak.hulapp.util.dialog
import pl.kamilszustak.hulapp.ui.view.authorization.item.CityItem
import pl.kamilszustak.hulapp.ui.view.authorization.item.CountryItem
import pl.kamilszustak.hulapp.ui.view.base.BaseFragment
import pl.kamilszustak.hulapp.ui.view.dialog.CityChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.view.dialog.CountryChoiceBottomSheet
import pl.kamilszustak.hulapp.ui.viewmodel.authorization.SignUpViewModel
import pl.kamilszustak.hulapp.util.navigateUp
import javax.inject.Inject

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private lateinit var countryChoiceBottomSheet: CountryChoiceBottomSheet
    private lateinit var cityChoiceBottomSheet: CityChoiceBottomSheet

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: SignUpViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        ).apply {
            this.viewModel = this@SignUpFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
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
        signUpButton.setOnClickListener {
            viewModel.signUp()
        }

        backToLoginPageButton.setOnClickListener {
            navigateUp()
        }

        cityNameEditText.setOnClickListener {
            cityChoiceBottomSheet.show(
                childFragmentManager,
                CityChoiceBottomSheet.tag
            )
        }

        countryNameEditText.setOnClickListener {
            countryChoiceBottomSheet.show(
                childFragmentManager,
                CountryChoiceBottomSheet.tag
            )
        }
    }

    private fun observeViewModel() {
        viewModel.isSigningUpInProgress.observe(this) {
            if (it) {
                signUpButton.isEnabled = false
                progressBar.show()
            } else {
                signUpButton.isEnabled = true
                progressBar.hide()
            }
        }

        viewModel.signUpError.observe(this) {
            view?.snackbar(it)
        }

        viewModel.userSignedUp.observe(this) {
            dialog {
                title(R.string.user_signed_up_title)
                message(R.string.user_signed_up_message)
                positiveButton(R.string.ok) {
                    it.dismiss()
                }
            }
        }
    }
}