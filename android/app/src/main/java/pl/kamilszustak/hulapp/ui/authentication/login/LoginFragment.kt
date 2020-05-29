package pl.kamilszustak.hulapp.ui.authentication.login

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
import pl.kamilszustak.hulapp.databinding.FragmentLoginBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.ui.main.MainActivity
import pl.kamilszustak.hulapp.util.navigateTo
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        ).apply {
            this.viewModel = this@LoginFragment.viewModel
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
        binding.passwordResetButton.setOnClickListener {
            navigateToPasswordResetFragment()
        }

        binding.signUpButton.setOnClickListener {
            navigateToSignUpFragment()
        }

        binding.loginButton.setOnClickListener {
            viewModel.login()
        }
    }

    private fun observeViewModel() {
        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            startActivity<MainActivity>()
            requireActivity().finish()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                with(binding) {
                    motionLayout.transitionToEnd()
                    loginButton.isEnabled = false
                    progressBar.show()
                }
            } else {
                with(binding) {
                    motionLayout.transitionToStart()
                    loginButton.isEnabled = true
                    progressBar.hide()
                }
            }
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }
    }

    private fun navigateToSignUpFragment() {
        val direction = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        navigateTo(direction)
    }

    private fun navigateToPasswordResetFragment() {
        val direction = LoginFragmentDirections.actionLoginFragmentToPasswordResetFragment()
        navigateTo(direction)
    }
}