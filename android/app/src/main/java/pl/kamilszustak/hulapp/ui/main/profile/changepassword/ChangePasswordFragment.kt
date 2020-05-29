package pl.kamilszustak.hulapp.ui.main.profile.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentChangePasswordBinding
import pl.kamilszustak.hulapp.ui.authentication.AuthenticationActivity
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import javax.inject.Inject

class ChangePasswordFragment : BaseFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: ChangePasswordViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentChangePasswordBinding>(
            inflater,
            R.layout.fragment_change_password,
            container,
            false
        ).apply {
            this.viewModel = this@ChangePasswordFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModel()
    }

    private fun setListeners() {
        binding.changePasswordButton.setOnClickListener {
            viewModel.onChangePasswordButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.errorEvent.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }

        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            startActivity<AuthenticationActivity>()
            requireActivity().finish()
        }
    }
}