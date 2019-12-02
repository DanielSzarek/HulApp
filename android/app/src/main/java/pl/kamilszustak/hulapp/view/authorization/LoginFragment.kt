package pl.kamilszustak.hulapp.view.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.toast
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentLoginBinding
import pl.kamilszustak.hulapp.util.getAndroidViewModelFactory
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.viewmodel.authorization.LoginViewModel
import timber.log.Timber

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels {
        getAndroidViewModelFactory()
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
        resetPasswordButton.setOnClickListener {
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
            toast("Logowanie zakończowe sukcesem")
        }

        viewModel.isLoggingInProgress.observe(this) {
            Timber.i(it.toString())
            if (it) {
                loginButton.isEnabled = false
                progressBar.show()
            } else {
                loginButton.isEnabled = true
                progressBar.hide()
            }
        }

        viewModel.loginError.observe(this) {
            view?.snackbar(it)
        }
    }
}