package pl.kamilszustak.hulapp.ui.view.main.profile

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentProfileBinding
import pl.kamilszustak.hulapp.ui.view.authorization.AuthorizationActivity
import pl.kamilszustak.hulapp.ui.view.base.BaseFragment
import pl.kamilszustak.hulapp.ui.viewmodel.main.profile.ProfileViewModel
import javax.inject.Inject

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: ProfileViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        ).apply {
            this.viewModel = this@ProfileFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setListeners()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutItem -> {
                viewModel.onLogoutItemClick()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setListeners() {

    }

    private fun observeViewModel() {
        viewModel.logoutEvent.observe(this) {
            startActivity<AuthorizationActivity>()
            requireActivity().finish()
        }

        viewModel.userResource.error.observe(this) {
            toast("Wystąpił błąd podczas ładowania profilu użytkownika")
        }
    }
}