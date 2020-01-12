package pl.kamilszustak.hulapp.ui.authorization.passwordreset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_password_reset.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentPasswordResetBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateTo
import javax.inject.Inject

class PasswordResetFragment : BaseFragment(R.layout.fragment_password_reset) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: PasswordResetViewModel by viewModels {
        viewModelFactory
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
        passwordResetButton.setOnClickListener {
            viewModel.onPasswordResetButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.resetError.observe(this) {
            view?.snackbar(it)
        }

        viewModel.resetInProgress.observe(this) {
            if (it) {
                motionLayout.transitionToEnd()
                passwordResetButton.isEnabled = false
                progressBar.show()
            } else {
                motionLayout.transitionToStart()
                passwordResetButton.isEnabled = true
                progressBar.hide()
            }
        }

        viewModel.resetCompleted.observe(this) {
            val direction = PasswordResetFragmentDirections.actionPasswordResetFragmentToPasswordResetCompletedFragment()
            navigateTo(direction)
        }
    }
}