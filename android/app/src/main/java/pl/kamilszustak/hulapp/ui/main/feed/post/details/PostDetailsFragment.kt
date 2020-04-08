package pl.kamilszustak.hulapp.ui.main.feed.post.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentPostDetailsBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.share
import javax.inject.Inject

class PostDetailsFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: PostDetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentPostDetailsBinding
    private val args: PostDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentPostDetailsBinding>(
            inflater,
            R.layout.fragment_post_details,
            container,
            false
        ).apply {
            this.viewModel = this@PostDetailsFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModel()
        viewModel.loadData(args.postId)
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        binding.postLayout.shareButton.setOnClickListener {
            viewModel.onShareButtonClick()
        }
    }

    private fun observeViewModel() {
        viewModel.sharePostEvent.observe(viewLifecycleOwner) { event ->
            share(
                event.content,
                event.subject,
                event.chooserTitle
            )
        }
    }
}