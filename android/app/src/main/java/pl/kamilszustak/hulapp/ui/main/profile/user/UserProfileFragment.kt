package pl.kamilszustak.hulapp.ui.main.profile.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.domain.item.TrackItem
import pl.kamilszustak.hulapp.databinding.FragmentUserProfileBinding
import pl.kamilszustak.hulapp.ui.main.profile.BaseProfileFragment
import pl.kamilszustak.hulapp.util.navigateTo
import pl.kamilszustak.hulapp.util.updateModels
import javax.inject.Inject

class UserProfileFragment : BaseProfileFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: UserProfileViewModel by viewModels {
        viewModelFactory
    }

    private val args: UserProfileFragmentArgs by navArgs()

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentUserProfileBinding>(
            inflater,
            R.layout.fragment_user_profile,
            container,
            false
        ).apply {
            this.viewModel = this@UserProfileFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setListeners()
        observeViewModel()
        viewModel.loadData(args.userId)
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
                    navigateToUserTrackDetailsFragment(item.model.id)
                    return true
                }
            }
        }

        binding.tracksRecyclerView.apply {
            this.adapter = fastAdapter
            this.itemAnimator = FadeInAnimator()
        }
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadData(args.userId, true)
        }

        binding.showAllTracksButton.setOnClickListener {
            navigateToUserTrackingHistoryFragment(args.userId)
        }
    }

    private fun observeViewModel() {
        viewModel.tracksResource.data.observe(viewLifecycleOwner) { tracks ->
            trackModelAdapter.updateModels(tracks)
        }
    }

    private fun navigateToUserTrackDetailsFragment(trackId: Long) {
        val direction = UserProfileFragmentDirections.actionUserProfileFragmentToUserTrackDetailsFragment(trackId)
        navigateTo(direction)
    }

    private fun navigateToUserTrackingHistoryFragment(userId: Long) {
        val direction = UserProfileFragmentDirections.actionUserProfileFragmentToUserTrackingHistoryFragment(userId)
        navigateTo(direction)
    }
}