package pl.kamilszustak.hulapp.view.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentSignUpBinding
import pl.kamilszustak.hulapp.util.dialog
import pl.kamilszustak.hulapp.util.getAndroidViewModelFactory
import pl.kamilszustak.hulapp.viewmodel.authorization.SignUpViewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels {
        getAndroidViewModelFactory()
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

        setListeners()
        observeViewModel()
    }

    private fun setListeners() {
        signUpButton.setOnClickListener {
            viewModel.signUp()
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