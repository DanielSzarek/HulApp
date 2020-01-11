package pl.kamilszustak.hulapp.ui.authorization.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentLoginBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.ui.main.MainActivity
import pl.kamilszustak.hulapp.util.navigateTo
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        ).apply {
            this.viewModel = this@LoginFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModel()
    }

    private fun setListeners() {
        passwordResetButton.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToPasswordResetFragment()
            navigateTo(direction)
        }

        signUpButton.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            navigateTo(direction)
        }

        loginButton.setOnClickListener {
            viewModel.login()
        }
    }

    private fun observeViewModel() {
        viewModel.loginCompleted.observe(this) {
            startActivity<MainActivity>()
            requireActivity().finish()
        }

        viewModel.isLoggingInProgress.observe(this) {
            if (it) {
                motionLayout.transitionToEnd()
                loginButton.isEnabled = false
                progressBar.show()
            } else {
                motionLayout.transitionToStart()
                loginButton.isEnabled = true
                progressBar.hide()
            }
        }

        viewModel.loginError.observe(this) {
            view?.snackbar(it)
        }
    }
}