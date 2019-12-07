package pl.kamilszustak.hulapp.ui.view.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_password_reset.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentPasswordResetBinding
import pl.kamilszustak.hulapp.util.dialog
import pl.kamilszustak.hulapp.util.getAndroidViewModelFactory
import pl.kamilszustak.hulapp.util.navigateUp
import pl.kamilszustak.hulapp.ui.viewmodel.authorization.PasswordResetViewModel

class PasswordResetFragment : Fragment(R.layout.fragment_password_reset) {

    private val viewModel: PasswordResetViewModel by viewModels {
        getAndroidViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentPasswordResetBinding>(
            inflater,
            R.layout.fragment_password_reset,
            container,
            false
        ).apply {
            this.viewModel = this@PasswordResetFragment.viewModel
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
            viewModel.resetPassword()
        }
    }

    private fun observeViewModel() {
        viewModel.resetError.observe(this) {
            view?.snackbar(it)
        }

        viewModel.resetInProgress.observe(this) {
            if (it) {
                resetPasswordButton.isEnabled = false
                progressBar.show()
            } else {
                resetPasswordButton.isEnabled = true
                progressBar.hide()
            }
        }

        viewModel.resetCompleted.observe(this) {
            dialog {
                title(R.string.password_reset_title)
                message(R.string.password_reset_message)
                positiveButton(R.string.ok) {
                    it.dismiss()
                    navigateUp()
                }
            }
        }
    }
}