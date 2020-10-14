package pl.kamilszustak.hulapp.ui.main.tracking.point.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentAddMapPointBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateUp
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModel()
        binding.ratingBar.rating
    }

    private fun setListeners() {
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            viewModel.onRatingChanged(rating)
        }

        binding.addPointButton.setOnClickListener {
            viewModel.onAddButtonClick(args.latLng)
        }
    }

    private fun observeViewModel() {
        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            navigateUp()
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { messageResource ->
            val message = getString(messageResource)
            view?.snackbar(message)
        }
    }
}