package pl.kamilszustak.hulapp.ui.main.tracking.point.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentAddMapPointBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import javax.inject.Inject

class AddMapPointFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: AddMapPointViewModel by viewModels {
        viewModelFactory
    }

    private val args: AddMapPointFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddMapPointBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAddMapPointBinding>(
            inflater,
            R.layout.fragment_add_map_point,
            container,
            false
        ).apply {
            this.viewModel = this@AddMapPointFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}