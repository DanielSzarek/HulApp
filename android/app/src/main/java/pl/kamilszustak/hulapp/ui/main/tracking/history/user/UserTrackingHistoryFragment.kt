package pl.kamilszustak.hulapp.ui.main.tracking.history.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_user_tracking_history.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentUserTrackingHistoryBinding
import pl.kamilszustak.hulapp.ui.main.tracking.history.BaseTrackingHistoryFragment
import pl.kamilszustak.hulapp.util.navigateTo
import javax.inject.Inject

class UserTrackingHistoryFragment : BaseTrackingHistoryFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: UserTrackingHistoryViewModel by viewModels {
        viewModelFactory
    }

    private val args: UserTrackingHistoryFragmentArgs by navArgs()

    override val recyclerView: RecyclerView
        get() = tracksRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentUserTrackingHistoryBinding>(
            inflater,
            R.layout.fragment_user_tracking_history,
            container,
            false
        ).apply {
            this.viewModel = this@UserTrackingHistoryFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        viewModel.loadData(args.userId)
    }

    private fun setListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    override fun navigateToTrackDetailsFragment(trackId: Long) {
        val direction = UserTrackingHistoryFragmentDirections.actionUserTrackingHistoryFragmentToUserTrackDetailsFragment(trackId)
        navigateTo(direction)
    }
}