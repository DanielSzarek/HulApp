package pl.kamilszustak.hulapp.ui.main.tracking.point.details

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentMapPointDetailsBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.dialog
import pl.kamilszustak.hulapp.util.navigateUp
import javax.inject.Inject

class MapPointDetailsFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: MapPointDetailsViewModel by viewModels {
        viewModelFactory
    }

    private val args: MapPointDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentMapPointDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMapPointDetailsBinding>(
            inflater,
            R.layout.fragment_map_point_details,
            container,
            false
        ).apply {
            this.viewModel = this@MapPointDetailsFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (args.isMapPointMine) {
            inflater.inflate(R.menu.menu_my_map_point, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteItem -> {
                onDeleteButtonClick()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setListeners()
        observeViewModel()
        viewModel.loadData(args.mapPointId)
    }

    private fun onDeleteButtonClick() {
        dialog {
            title(R.string.delete)
            message(R.string.delete_map_point_dialog_message)
            positiveButton(R.string.yes) { viewModel.onDeleteButtonClick(args.mapPointId) }
            negativeButton(R.string.no) { it.dismiss() }
        }
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun observeViewModel() {
        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            navigateUp()
        }
    }
}