package pl.kamilszustak.hulapp.ui.main.profile.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentProfileBinding
import pl.kamilszustak.hulapp.ui.authorization.AuthorizationActivity
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.ui.main.profile.main.photo.fullscreen.ProfilePhotoFullscreenDialogFragment
import pl.kamilszustak.hulapp.util.navigateTo
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
            R.id.editProfileItem -> {
                navigateToEditProfileFragment()
                true
            }

            R.id.changePasswordItem -> {
                navigateToChangePasswordFragment()
                true
            }

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
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        profilePhotoImageView.setOnClickListener {
            viewModel.openProfilePhoto()
        }

        profilePhotoImageView.setOnLongClickListener {
            val extras = FragmentNavigatorExtras(
                profilePhotoImageView to ProfilePhotoFullscreenDialogFragment.sharedElementTransitionName
            )
            val direction = ProfileFragmentDirections.actionProfileFragmentToProfilePhotoOptionsBottomSheet()
            navigateTo(direction, extras)

            true
        }
    }

    private fun observeViewModel() {
        viewModel.logoutEvent.observe(this) {
            startActivity<AuthorizationActivity>()
            requireActivity().finish()
        }

        viewModel.userResource.error.observe(this) {
            view?.snackbar("Wystąpił błąd podczas ładowania profilu użytkownika")
        }

        viewModel.openProfilePhoto.observe(this) {
            val direction = ProfileFragmentDirections.actionProfileFragmentToProfilePhotoFullscreenDialog(it)
            navigateTo(direction)
        }
    }

    private fun navigateToEditProfileFragment() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        navigateTo(direction)
    }

    private fun navigateToChangePasswordFragment() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
        navigateTo(direction)
    }
}