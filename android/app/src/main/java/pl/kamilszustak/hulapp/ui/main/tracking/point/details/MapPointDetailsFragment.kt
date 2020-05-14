package pl.kamilszustak.hulapp.ui.main.tracking.point.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentMapPointDetailsBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}