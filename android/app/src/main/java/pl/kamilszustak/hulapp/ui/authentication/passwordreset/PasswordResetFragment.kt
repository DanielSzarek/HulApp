package pl.kamilszustak.hulapp.ui.authentication.passwordreset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentPasswordResetBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateTo
import javax.inject.Inject

class PasswordResetFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: PasswordResetViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentPasswordResetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentPasswordResetBinding>(
            inflater,
            R.layout.fragment_password_reset,
            container,
            false
        ).apply {
            this.viewModel = this@PasswordResetFragment.viewModel
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
            viewModel.onPasswordResetButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.errorEvent.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }

        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            navigateToPasswordResetCompletedFragment()
        }
    }

    private fun navigateToPasswordResetCompletedFragment() {
        val direction = PasswordResetFragmentDirections.actionPasswordResetFragmentToPasswordResetCompletedFragment()
        navigateTo(direction)
    }
}