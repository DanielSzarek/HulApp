package pl.kamilszustak.hulapp.ui.main.feed.post.add

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.databinding.FragmentAddPostBinding
import pl.kamilszustak.hulapp.ui.base.BaseFragment
import pl.kamilszustak.hulapp.util.navigateUp
import javax.inject.Inject

class AddPostFragment : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val viewModel: AddPostViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentAddPostBinding
    private val args: AddPostFragmentArgs by navArgs()

    private val inEditMode: Boolean by lazy {
        args.postId != -1L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAddPostBinding>(
            inflater,
            R.layout.fragment_add_post,
            container,
            false
        ).apply {
            this.viewModel = this@AddPostFragment.viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_post_fragment, menu)

        menu.findItem(R.id.postActionItem)?.apply {
            this.title = if (inEditMode) {
                getString(R.string.edit_post_button_text)
            } else {
                getString(R.string.add_post_button_text)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.postActionItem -> {
                viewModel.onAddPostButtonClick(args.postId)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBarTitle()
        setHasOptionsMenu(true)
        observeViewModel()

        if (inEditMode) {
            viewModel.loadData(args.postId)
        }
    }

    private fun setActionBarTitle() {
        actionBarTitle = if (inEditMode) {
            getString(R.string.edit_post_action_bar_title)
        } else {
            getString(R.string.add_post_action_bar_title)
        }
    }

    private fun observeViewModel() {
        viewModel.actionCompletedEvent.observe(viewLifecycleOwner) {
            navigateUp()
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { messageResource ->
            view?.snackbar(messageResource)
        }
    }
}