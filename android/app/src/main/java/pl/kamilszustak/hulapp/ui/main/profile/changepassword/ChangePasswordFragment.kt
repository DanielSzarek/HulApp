package pl.kamilszustak.hulapp.ui.main.profile.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentChangePasswordBinding
import pl.kamilszustak.hulapp.ui.authorization.AuthorizationActivity
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import javax.inject.Inject

class ChangePasswordFragment : BaseFragment(R.layout.fragment_change_password) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: ChangePasswordViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentChangePasswordBinding>(
            inflater,
            R.layout.fragment_change_password,
            container,
            false
        ).apply {
            this.viewModel = this@ChangePasswordFragment.viewModel
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
        changePasswordButton.setOnClickListener {
            viewModel.onChangePasswordButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.isPasswordChanging.observe(this) {
            if (it) {
                progressBar.show()
            } else {
                progressBar.hide()
            }
        }

        viewModel.passwordChangeError.observe(this) {
            view?.snackbar(it)
        }

        viewModel.passwordChangeCompleted.observe(this) {
            startActivity<AuthorizationActivity>()
            requireActivity().finish()
        }
    }
}