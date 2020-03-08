package pl.kamilszustak.hulapp.ui.main.profile.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.data.item.TrackItem
import pl.kamilszustak.hulapp.databinding.FragmentProfileBinding
import pl.kamilszustak.hulapp.ui.authorization.AuthorizationActivity
import pl.kamilszustak.hulapp.ui.main.profile.BaseProfileFragment
import pl.kamilszustak.hulapp.util.navigateTo
import javax.inject.Inject

class ProfileFragment : BaseProfileFragment(R.layout.fragment_profile) {

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
        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchItem -> {
                navigateToSearchFragment()
                true
            }

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

    private fun initializeRecyclerView() {
        val fastAdapter = FastAdapter.with(trackModelAdapter).apply {
            this.onClickListener = object : ClickListener<TrackItem> {
                override fun invoke(
                    v: View?,
                    adapter: IAdapter<TrackItem>,
                    item: TrackItem,
                    position: Int
                ): Boolean {
                    navigateToTrackDetailsFragment(item.model.id)
                    return true
                }
            }
        }

        tracksRecyclerView.apply {
            this.adapter = fastAdapter
        }
    }

    private fun setListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        profilePhotoImageView.setOnClickListener {
            viewModel.openProfilePhoto()
        }

        addPhotoButton.setOnClickListener {
            navigateToProfilePhotoOptionsBottomSheet()
        }

        editProfileButton.setOnClickListener {
            navigateToEditProfileFragment()
        }

        showAllTracksButton.setOnClickListener {
            navigateToTrackingHistoryFragment()
        }
    }

    private fun observeViewModel() {
        viewModel.logoutEvent.observe(viewLifecycleOwner) {
            startActivity<AuthorizationActivity>()
            requireActivity().finish()
        }

        viewModel.userResource.error.observe(viewLifecycleOwner) { message ->
            view?.snackbar("Wystąpił błąd podczas ładowania profilu użytkownika")
        }

        viewModel.openProfilePhoto.observe(viewLifecycleOwner) { url ->
            navigateToProfilePhotoFullscreenDialog(url)
        }
    }

    private fun navigateToSearchFragment() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToSearchFragment()
        navigateTo(direction)
    }

    private fun navigateToEditProfileFragment() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        navigateTo(direction)
    }

    private fun navigateToChangePasswordFragment() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
        navigateTo(direction)
    }

    private fun navigateToProfilePhotoOptionsBottomSheet() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToProfilePhotoOptionsBottomSheet()
        navigateTo(direction)
    }

    private fun navigateToProfilePhotoFullscreenDialog(url: String) {
        val direction = ProfileFragmentDirections.actionProfileFragmentToProfilePhotoFullscreenDialog(url)
        navigateTo(direction)
    }

    private fun navigateToTrackDetailsFragment(trackId: Long) {
        val direction = ProfileFragmentDirections.actionProfileFragmentToTrackDetailsFragment(trackId)
        navigateTo(direction)
    }

    private fun navigateToTrackingHistoryFragment() {

    }
}