package pl.kamilszustak.hulapp.ui.main.tracking.details.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_user_track_details.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentUserTrackDetailsBinding
import pl.kamilszustak.hulapp.ui.main.tracking.details.base.BaseTrackDetailsFragment
import javax.inject.Inject

class UserTrackDetailsFragment : BaseTrackDetailsFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: UserTrackDetailsViewModel by viewModels {
        viewModelFactory
    }

    private val args: UserTrackDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentUserTrackDetailsBinding>(
            inflater,
            R.layout.fragment_user_track_details,
            container,
            false
        ).apply {
            this.viewModel = this@UserTrackDetailsFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModel()
        viewModel.loadData(args.trackId)
    }

    private fun setListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun observeViewModel() {
        viewModel.trackResource.error.observe(viewLifecycleOwner) { message ->
            view?.snackbar(message)
        }
    }
}