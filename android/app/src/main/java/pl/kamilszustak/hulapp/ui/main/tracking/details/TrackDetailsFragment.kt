package pl.kamilszustak.hulapp.ui.main.tracking.details

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentTrackDetailsBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateUp
import javax.inject.Inject

class TrackDetailsFragment : BaseFragment(R.layout.fragment_track_details) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val viewModel: TrackDetailsViewModel by viewModels {
        viewModelFactory
    }

    private val args: TrackDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentTrackDetailsBinding>(
            inflater,
            R.layout.fragment_track_details,
            container,
            false
        ).apply {
            this.viewModel = this@TrackDetailsFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        observeViewModel()
        viewModel.loadTrack(args.trackId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_track_details_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteTrackItem -> {
                viewModel.onDeleteTrackButtonClick()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.deletingCompleted.observe(this) {
            navigateUp()
        }

        viewModel.error.observe(this) {
            view?.snackbar(it)
        }

        viewModel.trackResource.error.observe(this) {
            view?.snackbar(it)
        }
    }
}